package com.turkcell.LibraryAppDb.rules;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;
import com.turkcell.LibraryAppDb.repository.CustomerRepository;

@Component
public class CustomerBusinessRules {

	private final CustomerRepository customerRepository;

	public CustomerBusinessRules(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void ensureCustomerExists(int id) {
		if (!customerRepository.existsById(id)) {
			throw new IllegalArgumentException("Müşteri bulunamadı.");
		}
	}

	public void ensureEmailUnique(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Email zorunludur.");
		}
		if (customerRepository.existsByEmailIgnoreCase(email)) {
			throw new IllegalArgumentException("Email benzersiz olmalı.");
		}
	}

	public void ensureNotBanned(MemberStatus status) {
		if (status == MemberStatus.BANNED) {
			throw new IllegalStateException("Üye yasaklı olduğu için işlem yapamaz!");
		}
	}

}