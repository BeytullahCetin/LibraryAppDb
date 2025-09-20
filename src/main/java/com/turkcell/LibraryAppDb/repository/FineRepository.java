package com.turkcell.LibraryAppDb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Fine;

public interface FineRepository extends JpaRepository<Fine, Integer> {

    List<Fine> findByBorrow_Customer_Id(int customerId);

    boolean existsByBorrow_Customer_IdAndIsPaid(int customerId, boolean isPaid);
}
