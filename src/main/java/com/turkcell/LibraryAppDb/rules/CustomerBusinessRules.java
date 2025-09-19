package com.turkcell.LibraryAppDb.rules;


import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;
import com.turkcell.LibraryAppDb.repository.BorrowRepository;
import com.turkcell.LibraryAppDb.repository.CustomerRepository;
import com.turkcell.LibraryAppDb.repository.FineRepository;

@Component
public class CustomerBusinessRules {

	private final CustomerRepository customerRepository;
	private final BorrowRepository borrowRepository;
	private final FineRepository fineRepository;



	public CustomerBusinessRules(CustomerRepository customerRepository, BorrowRepository borrowRepository, FineRepository fineRepository) {
		this.customerRepository = customerRepository;
		this.borrowRepository = borrowRepository;
		this.fineRepository = fineRepository;
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

	public void ensureBorrowLimitNotExceeded(int customerId, MemberStatus status) {
		long openBorrows = borrowRepository.countByCustomer_IdAndReturnDateIsNull(customerId);
		int limit = switch (status) {
			case GOLD -> 5;
			case STANDARD -> 3;
			case BANNED -> 0;
		};
		if (openBorrows >= limit) {
			throw new IllegalStateException("Üyelik seviyesine göre aktif ödünç limiti aşıldı.");
		}
	}

	public void ensureNoOpenFines(int customerId) {
		long fines = fineRepository.countByBorrow_Customer_Id(customerId);
		if (fines > 0) {
			throw new IllegalStateException("Açık ceza mevcutken yeni ödünç alınamaz.");
		}
	}
}