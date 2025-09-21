package com.turkcell.LibraryAppDb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.LibraryAppDb.entity.Reservation;
import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;

public interface ReservationRepostiory extends JpaRepository<Reservation, Integer> {
	boolean existsByCustomer_IdAndBook_IdAndReservationStatus(int customerId, int bookId, ReservationStatus status);

	Optional<Reservation> findTop1ByBook_Id(int bookId);
}
