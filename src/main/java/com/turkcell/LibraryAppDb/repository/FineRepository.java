package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Fine;

public interface FineRepository extends JpaRepository<Fine, Integer> {
    long countByBorrow_Customer_Id(int customerId);
    
}
