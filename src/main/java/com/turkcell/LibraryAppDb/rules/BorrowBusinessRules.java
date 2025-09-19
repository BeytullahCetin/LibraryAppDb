package com.turkcell.LibraryAppDb.rules;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import com.turkcell.LibraryAppDb.entity.BookCopy;
import com.turkcell.LibraryAppDb.entity.Borrow;
import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;
import com.turkcell.LibraryAppDb.repository.BookCopyRepository;
import com.turkcell.LibraryAppDb.repository.FineRepository;

@Component
public class BorrowBusinessRules {

	private final BookCopyRepository bookCopyRepository;
	private final FineRepository fineRepository;


	public BorrowBusinessRules(BookCopyRepository bookCopyRepository, FineRepository fineRepository) {
		this.bookCopyRepository = bookCopyRepository;
		this.fineRepository = fineRepository;
	}

	public void ensureBookCopyAvailable(int bookCopyId) {
		BookCopy copy = bookCopyRepository.findById(bookCopyId)
				.orElseThrow(() -> new NotFoundException("Kopya bulunamadı."));
		if (copy.getBookStatus() != BookStatus.AVAILABLE) {
			throw new IllegalStateException("Seçilen kopya şu anda müsait değil.");
		}
	}

	public void ensureNoOpenFines(int customerId) {
		long fines = fineRepository.countByBorrow_Customer_Id(customerId);
		if (fines > 0) {
			throw new IllegalStateException("Ödenmemiş ceza mevcutken yeni ödünç alınamaz.");
		}
	}

	public Date calculateDueDate(Date loanDate, MemberStatus status) {
		int days = switch (status) {
			case GOLD -> 21;
			case STANDARD -> 14;
			case BANNED -> 0;
		};
		if (days == 0) throw new IllegalStateException("Üye yasaklı olduğu için işlem yapamaz!");
		Calendar cal = Calendar.getInstance();
		cal.setTime(loanDate);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public void handleReturn(Borrow borrow) {
		if (borrow.getReturnDate() == null) {
			throw new IllegalArgumentException("İade tarihi zorunludur.");
		}
        //FineBusinessRulesa gidip ceza hesabı yapalım
        //İadeye geçelim
        // Kopyayı tekrar AVAILABLE yap
		BookCopy copy = bookCopyRepository.findById(borrow.getBookCopy().getId())
				.orElseThrow(() -> new NotFoundException("Kopya bulunamadı."));
		copy.setBookStatus(BookStatus.AVAILABLE);
		bookCopyRepository.save(copy);
	}
}