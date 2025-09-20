package com.turkcell.LibraryAppDb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.customer.request.CreateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.request.UpdateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.response.CreatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.DeletedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.GetByIdCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.UpdatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.fine.response.GetFineByCustomerIdResponse;
import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;
import com.turkcell.LibraryAppDb.service.CustomerService;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CreatedCustomerResponse add(@RequestBody CreateCustomerRequest customerDto) {
		return customerService.add(customerDto);
	}

	@GetMapping("{id}")
	public GetByIdCustomerResponse getById(@PathVariable int id) {
		return customerService.getById(id);
	}

	// GET /api/members?status=...&email=...
	@GetMapping(params = {"status", "email"})
	public List<GetByIdCustomerResponse> getByStatusAndEmail(@RequestParam MemberStatus status, @RequestParam String email) {
		return customerService.getByStatusAndEmail(status, email);
	}

	// PATCH /api/members/{id}/status?value=...
	@PatchMapping("{id}/status")
	public UpdatedCustomerResponse updateStatus(@PathVariable int id, @RequestParam("value") MemberStatus value) {
		return customerService.updateStatus(id, value);
	}

	// GET /api/members/{id}/fines?isPaid=false
	@GetMapping("{id}/fines")
	public List<GetFineByCustomerIdResponse> getFines(@PathVariable int id, @RequestParam boolean isPaid) {
		return customerService.getFinesByCustomerIdAndIsPaid(id, isPaid);
	}

	@PutMapping("{id}")
	public UpdatedCustomerResponse update(@PathVariable int id, @RequestBody UpdateCustomerRequest customerDto) {
		return customerService.update(id, customerDto);
	}

	@DeleteMapping("{id}")
	public DeletedCustomerResponse delete(@PathVariable int id) {
		return customerService.delete(id);
	}
}