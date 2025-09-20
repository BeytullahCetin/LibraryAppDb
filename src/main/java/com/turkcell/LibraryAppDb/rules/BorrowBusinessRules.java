package com.turkcell.LibraryAppDb.rules;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;
import com.turkcell.LibraryAppDb.repository.BorrowRepository;

@Component
public class BorrowBusinessRules {
	private final BorrowRepository borrowRepository;

	public BorrowBusinessRules(BorrowRepository borrowRepository) {
		this.borrowRepository = borrowRepository;
	}

	public Date calculateDueDate(Date loanDate, MemberStatus status) {
		if (status == MemberStatus.BANNED) {
			throw new IllegalStateException("Üye yasaklı olduğu için işlem yapamaz!");
		}

		int days = switch (status) {
			case GOLD -> 21;
			case STANDARD -> 14;
			default -> throw new IllegalArgumentException("Unexpected value: " + status);
		};

		Calendar cal = Calendar.getInstance();
		cal.setTime(loanDate);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public void ensureSameBookNotAlreadyBorrowed(int customerId, int bookId) {
		if (borrowRepository.existsByCustomer_IdAndBookCopy_Book_Id(customerId, bookId)) {
			throw new IllegalStateException("Müşteri zaten bu kitabı ödünç almış.");
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

	public void ensureBorrowExists(int id) {
		if (!borrowRepository.existsById(id)) {
			throw new IllegalArgumentException("Ödünç alma bulunamadı.");
		}
	}

	

	// TODO: Servise eklenecek
	// public void handleReturn(Borrow borrow) {
	// if (borrow.getReturnDate() == null) {
	// throw new IllegalArgumentException("İade tarihi zorunludur.");
	// }
	// // FineBusinessRulesa gidip ceza hesabı yapalım
	// // İadeye geçelim
	// // Kopyayı tekrar AVAILABLE yap

	// copy.setBookStatus(BookStatus.AVAILABLE);
	// bookCopyRepository.save(copy);
	// }

}