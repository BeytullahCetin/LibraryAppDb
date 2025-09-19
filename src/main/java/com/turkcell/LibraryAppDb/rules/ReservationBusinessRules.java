package com.turkcell.LibraryAppDb.rules;

import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;
import com.turkcell.LibraryAppDb.repository.ReservationRepostiory;

public class ReservationBusinessRules {

	private final ReservationRepostiory reservationRepostiory;

	public ReservationBusinessRules(ReservationRepostiory reservationRepostiory) {
		this.reservationRepostiory = reservationRepostiory;
	}

	public void reservationStatusShouldBeValid(String status) {
		if (status == null || status.isBlank()) {
			throw new IllegalArgumentException("Reservation status is required.");
		}
		try {
			// Assuming ReservationStatus is an enum
			Enum.valueOf(ReservationStatus.class, status);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid reservation status: " + status);
		}
	}

	public void customerCannotReserveMultipleBooks(Customer customer) {
		boolean hasReserved = reservationRepostiory.hasCustomerReservedBook(customer);
		if (hasReserved) {
			throw new IllegalArgumentException("Customer has already reserved this book.");
		}
	}
}
