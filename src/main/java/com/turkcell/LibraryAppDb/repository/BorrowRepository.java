package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

}
