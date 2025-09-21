package com.turkcell.LibraryAppDb.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.turkcell.LibraryAppDb.entity.Reservation;
import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;

public interface ReservationRepostiory extends JpaRepository<Reservation, Integer> {
	boolean existsByCustomer_IdAndBook_IdAndReservationStatus(int customerId, int bookId, ReservationStatus status);

	Optional<Reservation> findTop1ByBook_Id(int bookId);

	// @Query("SELECT r FROM Reservation r WHERE r.reservationStatus = 'ACTIVE' AND
	// r.expireAt < :now")
	// List<Reservation> getExpiredReservations(Date now);

	List<Reservation> findByReservationStatusAndExpireAtAfter(ReservationStatus status, Date now);
}
