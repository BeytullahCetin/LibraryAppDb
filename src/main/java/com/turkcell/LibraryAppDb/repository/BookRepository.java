package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}