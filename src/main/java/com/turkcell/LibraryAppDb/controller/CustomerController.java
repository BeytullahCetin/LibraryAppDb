package com.turkcell.LibraryAppDb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.customer.request.CreateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.request.UpdateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.response.CreatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.DeletedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.GetByIdCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.UpdatedCustomerResponse;
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

	@PutMapping("{id}")
	public UpdatedCustomerResponse update(@PathVariable int id, @RequestBody UpdateCustomerRequest customerDto) {
		return customerService.update(id, customerDto);
	}

	@DeleteMapping("{id}")
	public DeletedCustomerResponse delete(@PathVariable int id) {
		return customerService.delete(id);
	}

}
