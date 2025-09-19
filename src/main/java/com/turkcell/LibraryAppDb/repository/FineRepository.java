package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.Fine;

public interface FineRepository extends JpaRepository<Fine, Integer> {
    long countByBorrow_Customer_Id(int customerId);

    // TODO: add query
    boolean isCustomerHasPreviouslyUnpaidFines(Customer customer);
}
