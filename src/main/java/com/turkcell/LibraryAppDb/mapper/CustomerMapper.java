package com.turkcell.LibraryAppDb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

import com.turkcell.LibraryAppDb.dto.customer.request.CreateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.request.UpdateCustomerRequest;
import com.turkcell.LibraryAppDb.dto.customer.response.CreatedCustomerResponse;
import com.turkcell.LibraryAppDb.dto.customer.response.UpdatedCustomerResponse;
import com.turkcell.LibraryAppDb.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	Customer createCustomerRequestToCustomer(CreateCustomerRequest request);

	CreatedCustomerResponse customerToCreatedCustomerResponse(Customer customer);

	Customer updateCustomerRequestToCustomer(UpdateCustomerRequest request);

	UpdatedCustomerResponse customerToUpdatedCustomerResponse(Customer customer);
}
