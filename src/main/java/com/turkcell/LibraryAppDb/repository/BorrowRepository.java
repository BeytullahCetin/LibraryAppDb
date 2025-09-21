package com.turkcell.LibraryAppDb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    long countByCustomer_IdAndReturnDateIsNull(int customerId);

    boolean existsByCustomer_IdAndBookCopy_Book_Id(int customerId, int bookId);

    List<Borrow> findByCustomer_Id(int customerId);
}
