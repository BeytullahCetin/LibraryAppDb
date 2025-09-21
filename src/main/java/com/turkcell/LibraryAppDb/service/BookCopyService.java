package com.turkcell.LibraryAppDb.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.LibraryAppDb.entity.Book;
import com.turkcell.LibraryAppDb.entity.BookCopy;
import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import com.turkcell.LibraryAppDb.repository.BookCopyRepository;
import com.turkcell.LibraryAppDb.rules.BookCopyBusinessRules;

@Service
public class BookCopyService {

	private final BookCopyRepository bookCopyRepository;
	private final BookCopyBusinessRules bookCopyBusinessRules;
	public BookCopyService(BookCopyRepository bookCopyRepository, BookCopyBusinessRules bookCopyBusinessRules) {
		this.bookCopyBusinessRules = bookCopyBusinessRules;
		this.bookCopyRepository = bookCopyRepository;
	}

	public void createCopies(Book book, int count) {
		for (int i = 0; i < count; i++) {
			BookCopy copy = new BookCopy();
			copy.setBook(book);
			copy.setBookStatus(BookStatus.AVAILABLE);
			copy.setAcquisionDate(new Date());
			bookCopyRepository.save(copy);
		}
	}

	public void removeAvailableCopies(int bookId, int count) {
		List<BookCopy> availCopies = bookCopyRepository.findByBook_IdAndBookStatus(bookId, BookStatus.AVAILABLE);
		List<BookCopy> toDelete = availCopies.stream().limit(count).collect(Collectors.toList());
		bookCopyRepository.deleteAll(toDelete);
	}

	public long countAvailableByBookId(int bookId) {
		return bookCopyRepository.countByBook_IdAndBookStatus(bookId, BookStatus.AVAILABLE);
	}

	public BookCopy findById(int bookCopyId) {
		return bookCopyBusinessRules.bookCopyMustExist(bookCopyId);
	}

	public void save(BookCopy bookCopy) {
		bookCopyRepository.save(bookCopy);
	}

}