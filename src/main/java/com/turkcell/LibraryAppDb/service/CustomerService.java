package com.turkcell.LibraryAppDb.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.webjars.NotFoundException;

import com.turkcell.LibraryAppDb.dto.customer.request.CreateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.request.UpdateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.response.CreatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.DeletedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.GetByIdCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.UpdatedCustomerResponse;
import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.repository.CustomerRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public CreatedCustomerResponse add(@Valid CreateCustomerRequest customerDto) {

		Customer customer = new Customer();
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		customer.setPhone(customerDto.getPhone());
		customer.setRegisterDate(new Date());

		customerRepository.save(customer);

		return new CreatedCustomerResponse(customer.getName(), customer.getPhone(), customer.getEmail());
	}

	public GetByIdCustomerResponse getById(int id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Bu id ile customer bulunamadı"));

		return new GetByIdCustomerResponse(customer.getName(), customer.getPhone(), customer.getEmail());
	}

	public UpdatedCustomerResponse update(int id, @Valid UpdateCustomerRequest customerDto) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Bu id ile customer bulunamadı"));
		customer.setName(customerDto.getName());
		customer.setEmail(customerDto.getEmail());
		customer.setPhone(customerDto.getPhone());

		customerRepository.save(customer);

		return new UpdatedCustomerResponse(customer.getName(), customer.getPhone(), customer.getEmail());

	}

	public DeletedCustomerResponse delete(int id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Bu id ile customer bulunamadı"));
		customerRepository.deleteById(id);
		return new DeletedCustomerResponse(customer.getName());
	}

}