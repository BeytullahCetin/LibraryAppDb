package com.turkcell.LibraryAppDb.rules;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.Book;
import com.turkcell.LibraryAppDb.repository.BookRepository;

@Component
public class BookBusinessRules {

	private final BookRepository bookRepository;
	// TODO: Add BoookCopyService
	// private final BookCopyRepository bookCopyRepository;

	public BookBusinessRules(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book bookShouldExistWithGivenId(int id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Book bulunamadı."));
	}

	public void bookCopyDeltaMustNotBeZero(int delta) {
		if (delta == 0) {
			throw new IllegalArgumentException("Delta sıfır olamaz.");
		}
	}

	public void ensureIsbnUnique(String isbn) {
		if (isbn == null || isbn.isBlank()) {
			throw new IllegalArgumentException("ISBN zorunludur.");
		}
		if (bookRepository.existsByIsbnIgnoreCase(isbn)) {
			throw new IllegalArgumentException("ISBN benzersiz olmalı.");
		}
	}

	public void ensureCanRemoveCopies(int bookId, int removeCount) {
		Book book = bookRepository.findById(bookId).get();
		if (book.getBookCopies().size() < removeCount) {
			throw new IllegalArgumentException("Kitabın bu kadar kopyası yok.");
		}
	}

}