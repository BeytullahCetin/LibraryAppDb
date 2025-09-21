package com.turkcell.LibraryAppDb.rules;

import java.util.List;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.BookCopy;
import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import com.turkcell.LibraryAppDb.repository.BookCopyRepository;

@Component
public class BookCopyBusinessRules {
	private final BookCopyRepository bookCopyRepository;

	public BookCopyBusinessRules(BookCopyRepository bookCopyRepository) {
		this.bookCopyRepository = bookCopyRepository;
	}

	public BookCopy ensureBookCopyAvailable(int bookId) {
		List<BookCopy> copies = bookCopyRepository.findByBook_IdAndBookStatus(bookId, BookStatus.AVAILABLE);
		if (copies == null || copies.isEmpty()) {
			throw new IllegalArgumentException("Müsait kopya bulunamadı.");
		}
		BookCopy copy = copies.get(0);
		return copy;

	}

	public BookCopy bookCopyMustExist(int bookCopyId) {
		return bookCopyRepository.findById(bookCopyId)
				.orElseThrow(() -> new IllegalArgumentException("Kopya bulunamadı."));
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
