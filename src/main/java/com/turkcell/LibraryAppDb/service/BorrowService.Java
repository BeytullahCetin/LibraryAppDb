package com.turkcell.LibraryAppDb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.turkcell.LibraryAppDb.dto.borrow.request.CreateBorrowRequest;
import com.turkcell.LibraryAppDb.dto.borrow.request.UpdateBorrowRequest;
import com.turkcell.LibraryAppDb.dto.borrow.response.CreatedBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.DeletedBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.GetByCustomerIdBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.GetByIdBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.UpdatedBorrowResponse;
import com.turkcell.LibraryAppDb.entity.Borrow;
import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.BookCopy;
import com.turkcell.LibraryAppDb.mapper.BorrowMapper;
import com.turkcell.LibraryAppDb.repository.BorrowRepository;
import com.turkcell.LibraryAppDb.rules.BorrowBusinessRules;
import com.turkcell.LibraryAppDb.rules.FineBusinessRules;
import com.turkcell.LibraryAppDb.rules.BookCopyBusinessRules;

import jakarta.validation.Valid;

@Service
@Validated
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final BorrowBusinessRules borrowBusinessRules;
    private final BorrowMapper borrowMapper;
    private final CustomerService customerService;
    private final FineBusinessRules fineBusinessRules;
    private final FineService fineService;
    private final BookCopyBusinessRules bookCopyBusinessRules;
    private final BookCopyService bookCopyService;

    public BorrowService(BorrowRepository borrowRepository, BorrowBusinessRules borrowBusinessRules,
            BorrowMapper borrowMapper, CustomerService customerService, FineBusinessRules fineBusinessRules,
            FineService fineService, BookCopyBusinessRules bookCopyBusinessRules, BookCopyService bookCopyService) {
        this.borrowRepository = borrowRepository;
        this.borrowBusinessRules = borrowBusinessRules;
        this.borrowMapper = borrowMapper;
        this.customerService = customerService;
        this.fineBusinessRules = fineBusinessRules;
        this.fineService = fineService;
        this.bookCopyBusinessRules = bookCopyBusinessRules;
        this.bookCopyService = bookCopyService;
    }

    public CreatedBorrowResponse add(@Valid CreateBorrowRequest borrowDto) {
        Customer customer = customerService.getCustomerById(borrowDto.getCustomerId());
        borrowBusinessRules.ensureBorrowLimitNotExceeded(borrowDto.getCustomerId(), customer.getMemberStatus());
        borrowBusinessRules.ensureSameBookNotAlreadyBorrowed(borrowDto.getCustomerId(), borrowDto.getBookCopyId());
        fineBusinessRules.customerCannotBorrowOrReservationWhenHasUnpaidFine(borrowDto.getCustomerId());

        // TODO:
        // Kitap ACTIVE mi, availableCopies > 0 mu? → ret değilse → devam
        // Kitap kopyasının aktif ve ödünç verilebilir olup olmadığını kontrol et
        bookCopyBusinessRules.ensureBookCopyAvailable(borrowDto.getBookCopyId());

        Borrow borrow = borrowMapper.createBorrowRequestToBorrow(borrowDto);
        borrow.setDueDate(borrowBusinessRules.calculateDueDate(borrowDto.getBorrDate(), customer.getMemberStatus()));

        borrowRepository.save(borrow);

        // Kitap kopyasının durumunu BORROWED olarak güncelle
        bookCopyBusinessRules.bookCopyMustExist(borrowDto.getBookCopyId());
        BookCopy bookCopy = bookCopyService.findById(borrowDto.getBookCopyId());
        bookCopy.setBookStatus(com.turkcell.LibraryAppDb.entity.enums.BookStatus.BORROWED);
        bookCopyBusinessRules.bookCopyRepository.save(bookCopy);

        return borrowMapper.borrowToCreatedBorrowResponse(borrow);
    }

    // PATCH /api/borrows/{id}
    public UpdatedBorrowResponse update(int id, @Valid UpdateBorrowRequest borrowDto) {
        borrowBusinessRules.ensureBorrowExists(id);
        Borrow borrow = borrowRepository.findById(id).get();
        borrow = borrowMapper.updateBorrowRequestToBorrow(borrowDto);
        borrowRepository.save(borrow);

        // TODO: Check if the book is returned late and create a fine if necessary
        // if (borrow.getReturnDate() != null &&
        // borrow.getReturnDate().after(borrow.getDueDate())) {
        // fineService.createFine(borrow, null);
        // }

        // TODO: Available copies +1 yapılması

        // TODO: Check if there are any active reservations, if there is one create
        // borrow for the customer and decrease available copies

        return borrowMapper.borrowToUpdatedBorrowResponse(borrow);
    }

    public DeletedBorrowResponse delete(int id) {
        borrowBusinessRules.ensureBorrowExists(id);
        Borrow borrow = borrowRepository.findById(id).get();
        borrowRepository.delete(borrow);
        return borrowMapper.borrowToDeletedBorrowResponse(borrow);
    }

    // GET /api/borrows/{id}
    public GetByIdBorrowResponse getById(int id) {
        borrowBusinessRules.ensureBorrowExists(id);
        Borrow borrow = borrowRepository.findById(id).get();
        return borrowMapper.borrowToGetByIdBorrowResponse(borrow);
    }

    // GET /api/borrows
    public List<GetByIdBorrowResponse> getAll() {
        List<Borrow> borrows = borrowRepository.findAll();
        return borrowMapper.borrowToGetByIdBorrowResponse(borrows);
    }

    // GET /api/loans/members/{memberId}?status=OPEN
    public List<GetByCustomerIdBorrowResponse> getByCustomerIdAll(int customerId) {
        List<Borrow> borrows = borrowRepository.findByCustomer_Id(customerId);
        return borrowMapper.borrowToGetByCustomerIdBorrowResponse(borrows);
    }
}