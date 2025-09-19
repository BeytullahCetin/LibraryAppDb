package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.turkcell.LibraryAppDb.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("select b from Book b where lower(b.title) like lower(concat('%', :keyword, '%'))")
	List<Book> searchByTitle(@Param("keyword") String keyword);

	@Query("select b from Book b where lower(b.language.name) = lower(:languageName)")
	List<Book> findByLanguageName(@Param("languageName") String languageName);

	boolean existsByIsbnIgnoreCase(String isbn);
}