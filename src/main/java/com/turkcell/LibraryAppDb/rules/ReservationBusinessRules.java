package com.turkcell.LibraryAppDb.rules;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.Reservation;
import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;
import com.turkcell.LibraryAppDb.repository.ReservationRepostiory;

@Component
public class ReservationBusinessRules {

	private final ReservationRepostiory reservationRepostiory;

	public ReservationBusinessRules(ReservationRepostiory reservationRepostiory) {
		this.reservationRepostiory = reservationRepostiory;
	}

	public Reservation reservationShouldExistWithGivenId(int id) {
		return reservationRepostiory.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + id));
	}

	public void onlyActiveReservationsCanBeCancelled(Reservation reservation) {
		if (reservation.getReservationStatus() != ReservationStatus.ACTIVE) {
			throw new IllegalStateException("Only active reservations can be cancelled.");

		}
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

	public void customerCannotHaveMultipleReservationsForSameBook(int customerId, int bookId) {
		if (reservationRepostiory.existsByCustomer_IdAndBook_IdAndReservationStatus(customerId, bookId,
				ReservationStatus.ACTIVE)) {
			throw new IllegalArgumentException("Customer has already reserved this book.");
		}
	}
}
