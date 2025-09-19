package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.BookCopy;
import com.turkcell.LibraryAppDb.entity.enums.BookStatus;

public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {
	long countByBook_Id(int bookId);
	long countByBook_IdAndBookStatus(int bookId, BookStatus status);
	boolean existsByIdAndBookStatus(int id, BookStatus status);
}