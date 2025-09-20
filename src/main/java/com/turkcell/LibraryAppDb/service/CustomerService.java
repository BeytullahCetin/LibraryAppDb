package com.turkcell.LibraryAppDb.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.turkcell.LibraryAppDb.dto.customer.request.CreateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.request.UpdateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.response.CreatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.DeletedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.GetByIdCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.UpdatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.fine.response.GetFineByCustomerIdResponse;
import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;
import com.turkcell.LibraryAppDb.mapper.CustomerMapper;
import com.turkcell.LibraryAppDb.mapper.FineMapper;
import com.turkcell.LibraryAppDb.repository.CustomerRepository;
import com.turkcell.LibraryAppDb.rules.CustomerBusinessRules;

import jakarta.validation.Valid;

@Service
@Validated
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerBusinessRules customerBusinessRules;

	public CustomerService(CustomerRepository customerRepository, CustomerBusinessRules customerBusinessRules) {
		this.customerRepository = customerRepository;
		this.customerBusinessRules = customerBusinessRules;
	}

	public CreatedCustomerResponse add(@Valid CreateCustomerRequest customerDto) {
		// Kurallar: email benzersiz olmalı
		customerBusinessRules.ensureEmailUnique(customerDto.getEmail());

		CustomerMapper customerMapper = CustomerMapper.INSTANCE;
		Customer customer = customerMapper.createCustomerRequestToCustomer(customerDto);
		customer.setRegisterDate(new Date());
		customerRepository.save(customer);

		return customerMapper.customerToCreatedCustomerResponse(customer);
	}

	public GetByIdCustomerResponse getById(int id) {
		// Kurallar: müşteri var mı?
		Customer customer = customerBusinessRules.customerShouldExistWithGivenId(id);
		return new GetByIdCustomerResponse(customer.getName(), customer.getPhone(), customer.getEmail());
	}

	public Customer getCustomerById(int id) {
		return customerRepository.findById(id).get();
	}

	public UpdatedCustomerResponse update(int id, @Valid UpdateCustomerRequest customerDto) {
		// Kurallar: müşteri var mı?
		Customer customer = customerBusinessRules.customerShouldExistWithGivenId(id);

		// Kurallar: email değişiyorsa benzersiz olmalı
		String oldEmail = customer.getEmail();
		String newEmail = customerDto.getEmail();
		if (newEmail != null && (oldEmail == null || !oldEmail.equalsIgnoreCase(newEmail))) {
			customerBusinessRules.ensureEmailUnique(newEmail);
		}

		// Alanları güncelle (mapper'a gerek olmadan sadece değişen alanlar)
		customer.setName(customerDto.getName());
		customer.setPhone(customerDto.getPhone());
		customer.setEmail(customerDto.getEmail());

		customerRepository.save(customer);

		CustomerMapper customerMapper = CustomerMapper.INSTANCE;
		return customerMapper.customerToUpdatedCustomerResponse(customer);
	}

	public DeletedCustomerResponse delete(int id) {
		Customer customer = customerBusinessRules.customerShouldExistWithGivenId(id);
		customerRepository.deleteById(id);
		return new DeletedCustomerResponse(customer.getName());
	}

	// GET /api/members?status=...&email=...
	public List<GetByIdCustomerResponse> getByStatusAndEmail(MemberStatus status, String email) {
		List<Customer> customers = customerRepository.findByMemberStatusAndEmailContainingIgnoreCase(status, email);
		return customers.stream()
				.map(c -> new GetByIdCustomerResponse(c.getName(), c.getPhone(), c.getEmail()))
				.collect(Collectors.toList());
	}

	// PATCH /api/members/{id}/status?value=...
	public UpdatedCustomerResponse updateStatus(int id, MemberStatus value) {
		Customer customer = customerBusinessRules.customerShouldExistWithGivenId(id);
		customer.setMemberStatus(value);
		customerRepository.save(customer);

		CustomerMapper customerMapper = CustomerMapper.INSTANCE;
		return customerMapper.customerToUpdatedCustomerResponse(customer);
	}

	// GET /api/members/{id}/fines?isPaid=false
	public List<GetFineByCustomerIdResponse> getFinesByCustomerIdAndIsPaid(int id, boolean isPaid) {
		Customer customer = customerBusinessRules.customerShouldExistWithGivenId(id);
		FineMapper fineMapper = FineMapper.INSTANCE;

		return customer.getBorrows().stream()
				.filter(b -> b.getFines() != null)
				.flatMap(b -> b.getFines().stream())
				.filter(f -> f.isIsPaid() == isPaid)
				.map(fineMapper::fineToGetFineByCustomerId)
				.collect(Collectors.toList());
	}
}