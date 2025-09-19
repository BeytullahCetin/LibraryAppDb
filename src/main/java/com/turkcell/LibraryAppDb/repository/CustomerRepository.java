package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmailIgnoreCase(String email);
}