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
import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import com.turkcell.LibraryAppDb.entity.enums.FineType;

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
        borrowBusinessRules.ensureSameBookNotAlreadyBorrowed(borrowDto.getCustomerId(), borrowDto.getBookId());
        fineBusinessRules.customerCannotBorrowOrReservationWhenHasUnpaidFine(borrowDto.getCustomerId());

        // TODO:
        // Kitap ACTIVE mi, availableCopies > 0 mu? → ret değilse → devam
        //borrowDTO neden bookcopyid geliyor? 
        // Kitap kopyasının aktif ve ödünç verilebilir olup olmadığını kontrol et
        BookCopy bookCopy = bookCopyBusinessRules.ensureBookCopyAvailable(borrowDto.getBookId());

        

        

        // Kitap kopyasının durumunu BORROWED olarak güncelle
        bookCopy.setBookStatus(BookStatus.BORROWED);
        
        bookCopyService.save(bookCopy);
        Borrow borrow = borrowMapper.createBorrowRequestToBorrow(borrowDto);
        borrow.setBookCopy(bookCopy);
        borrow.setDueDate(borrowBusinessRules.calculateDueDate(borrowDto.getBorrDate(), customer.getMemberStatus()));
        borrowRepository.save(borrow);
        return borrowMapper.borrowToCreatedBorrowResponse(borrow);
    }

    // PATCH /api/borrows/{id}
    public UpdatedBorrowResponse update(int id, @Valid UpdateBorrowRequest borrowDto) {
        borrowBusinessRules.ensureBorrowExists(id);
        Borrow borrow = borrowRepository.findById(id).get();
        borrow = borrowMapper.updateBorrowRequestToBorrow(borrowDto);
        borrowRepository.save(borrow);

        // Check if the book is returned late and create a fine if necessary
        if (borrow.getReturnDate() != null && borrow.getDueDate() != null &&
            borrow.getReturnDate().after(borrow.getDueDate())) {
            fineService.createFine(borrow, FineType.LATE);
        }
        //DONE: damage ve lost için fine oluşturulacak
        if (borrowDto.isDamage()) {
            fineService.createFine(borrow, FineType.DAMAGE);
        }
        if (borrowDto.isLost()) {
            fineService.createFine(borrow, FineType.LOST);
        }
        // Available copies +1 yapılması
        // Kitap kopyasının durumunu AVAILABLE olarak güncelle
        if (borrow.getBookCopy() != null) {
            BookCopy returnedCopy = bookCopyBusinessRules.bookCopyMustExist(borrow.getBookCopy().getId());
            returnedCopy.setBookStatus(BookStatus.AVAILABLE);
            bookCopyService.save(returnedCopy);
        }

        // Check if there are any active reservations, if there is one create
        // borrow for the customer and decrease available copies
        // Not implemented: Reservation logic is not present in the current codebase.

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