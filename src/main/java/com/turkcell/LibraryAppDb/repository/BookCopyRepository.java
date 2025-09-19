package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.BookCopy;

public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {

}
