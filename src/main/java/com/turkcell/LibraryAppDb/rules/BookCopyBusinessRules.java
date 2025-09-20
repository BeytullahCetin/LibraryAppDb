package com.turkcell.LibraryAppDb.rules;

import com.turkcell.LibraryAppDb.entity.BookCopy;
import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import com.turkcell.LibraryAppDb.repository.BookCopyRepository;

public class BookCopyBusinessRules {
	private final BookCopyRepository bookCopyRepository;

	public BookCopyBusinessRules(BookCopyRepository bookCopyRepository) {
		this.bookCopyRepository = bookCopyRepository;
	}

	public void ensureBookCopyAvailable(int bookCopyId) {
		BookCopy copy = bookCopyRepository.findById(bookCopyId)
				.orElseThrow(() -> new IllegalArgumentException("Kopya bulunamadı."));

		if (copy.getBookStatus() != BookStatus.AVAILABLE) {
			throw new IllegalStateException("Seçilen kopya şu anda müsait değil.");
		}
	}

	public void bookCopyMustExist(int bookCopyId) {
		if (!bookCopyRepository.existsById(bookCopyId)) {
			throw new IllegalArgumentException("Kopya bulunamadı.");
		}
	}

	public void ensureCopiesConsistency(int bookId) {
		long totalCopies = bookCopyRepository.countByBook_Id(bookId);
		long availableCopies = bookCopyRepository.countByBook_IdAndBookStatus(bookId, BookStatus.AVAILABLE);

		if (totalCopies < 0) {
			throw new IllegalStateException("Kopya sayısı negatif olamaz.");
		}
		if (availableCopies < 0) {
			throw new IllegalStateException("Müsait kopya sayısı negatif olamaz.");
		}
		if (availableCopies > totalCopies) {
			throw new IllegalStateException("Müsait kopya sayısı toplam kopya sayısından fazla olamaz.");
		}
	}

	public void ensureBookBorrowable(int bookId) {
		long availableCopies = bookCopyRepository.countByBook_IdAndBookStatus(bookId, BookStatus.AVAILABLE);
		if (availableCopies <= 0) {
			throw new IllegalStateException("Kitap ödünç verilemez: müsait kopya yok.");
		}
	}
}
