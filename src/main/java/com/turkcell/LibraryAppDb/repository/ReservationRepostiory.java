package com.turkcell.LibraryAppDb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.Reservation;

public interface ReservationRepostiory extends JpaRepository<Reservation, Integer> {

	// TODO: parametre olarak customer entity'si alabilir mi?
	// TODO: add query
	boolean hasCustomerReservedBook(Customer customer);
}
