package com.turkcell.LibraryAppDb.rules;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.repository.BookRepository;

@Component
public class BookBusinessRules {

	private final BookRepository bookRepository;
	// TODO: Add BoookCopyService
	// private final BookCopyRepository bookCopyRepository;

	public BookBusinessRules(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void ensureIsbnUnique(String isbn) {
		if (isbn == null || isbn.isBlank()) {
			throw new IllegalArgumentException("ISBN zorunludur.");
		}
		if (bookRepository.existsByIsbnIgnoreCase(isbn)) {
			throw new IllegalArgumentException("ISBN benzersiz olmalı.");
		}
	}

}