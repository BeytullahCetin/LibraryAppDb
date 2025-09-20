package com.turkcell.LibraryAppDb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.borrow.request.CreateBorrowRequest;
import com.turkcell.LibraryAppDb.dto.borrow.request.UpdateBorrowRequest;
import com.turkcell.LibraryAppDb.dto.borrow.response.CreatedBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.GetByCustomerIdBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.UpdatedBorrowResponse;
import com.turkcell.LibraryAppDb.service.BorrowService;

@RestController
@RequestMapping("/api/")
public class BorrowController {

	private final BorrowService borrowService;

	public BorrowController(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	// POST /api/loans → LoanCreateRequest → LoanResponse
	@PostMapping("v1/borrows")
	public CreatedBorrowResponse createBorrow(CreateBorrowRequest request) {
		return borrowService.add(request);
	}

	// POST /api/loans/return → LoanReturnRequest → LoanResponse
	@PostMapping("v1/borrows/{id}/return")
	public UpdatedBorrowResponse returnBorrow(@PathVariable int id, UpdateBorrowRequest request) {
		return borrowService.update(id, request);
	}

	// GET /api/loans/members/{memberId}?status=OPEN
	public List<GetByCustomerIdBorrowResponse> getBorrowsByCustomerId(@PathVariable int customerId, String status) {
		// Implementation to retrieve borrows by customer ID and status
		return borrowService.getByCustomerIdAll(customerId);
	}
}
