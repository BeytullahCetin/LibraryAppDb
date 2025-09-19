package com.turkcell.LibraryAppDb.rules;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import com.turkcell.LibraryAppDb.repository.BookCopyRepository;
import com.turkcell.LibraryAppDb.repository.BookRepository;

@Component
public class BookBusinessRules {

	private final BookRepository bookRepository;
	private final BookCopyRepository bookCopyRepository;

	public BookBusinessRules(BookRepository bookRepository, BookCopyRepository bookCopyRepository) {
		this.bookRepository = bookRepository;
		this.bookCopyRepository = bookCopyRepository;
	}

	public void ensureIsbnUnique(String isbn) {
		if (isbn == null || isbn.isBlank()) {
			throw new IllegalArgumentException("ISBN zorunludur.");
		}
		if (bookRepository.existsByIsbnIgnoreCase(isbn)) {
			throw new IllegalArgumentException("ISBN benzersiz olmalı.");
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