package com.turkcell.LibraryAppDb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.turkcell.LibraryAppDb.dto.reservation.request.CreateReservationRequest;
import com.turkcell.LibraryAppDb.dto.reservation.response.CancelledReservationResponse;
import com.turkcell.LibraryAppDb.dto.reservation.response.CreatedReservationResponse;
import com.turkcell.LibraryAppDb.entity.Book;
import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.Reservation;
import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;
import com.turkcell.LibraryAppDb.mapper.ReservationMapper;
import com.turkcell.LibraryAppDb.repository.ReservationRepostiory;
import com.turkcell.LibraryAppDb.rules.ReservationBusinessRules;

import jakarta.validation.Valid;

@Service
@Validated
public class ReservationService {

	private final ReservationRepostiory reservationRepostiory;
	private final ReservationBusinessRules reservationBusinessRules;

	public ReservationService(ReservationRepostiory reservationRepostiory,
			ReservationBusinessRules reservationBusinessRules) {
		this.reservationRepostiory = reservationRepostiory;
		this.reservationBusinessRules = reservationBusinessRules;
	}

	// TODO: This method should be called by a scheduled task (e.g., daily)
	public void cancelExpiredReservations() {
		List<Reservation> reservations = reservationRepostiory.findAll();
		Date now = new Date();
		for (Reservation reservation : reservations) {
			if (reservation.getReservationStatus() == ReservationStatus.ACTIVE
					&& reservation.getExpireAt().before(now)) {
				reservation.setReservationStatus(ReservationStatus.EXPIRED);
				reservationRepostiory.save(reservation);
			}
		}
	}

	public CreatedReservationResponse reserve(@Valid CreateReservationRequest request) {

		ReservationMapper reservationMapper = ReservationMapper.INSTANCE;
		// TODO: Get customer by id
		// Customer customer = customerService.getById(request.getCustomerId());
		Customer customer = new Customer();
		customer.setId(request.getCustomerId());

		Book book = new Book();
		book.setId(request.getBookId());

		// TODO: (availableCopies > 0 -> return)
		// Check if the book copy is not available copies by book id
		reservationBusinessRules.customerCannotHaveMultipleReservationsForSameBook(customer.getId(), book.getId());

		Reservation reservation = new Reservation();
		reservation.setCustomer(customer);
		reservation.setBook(book);
		reservation.setReservationStatus(ReservationStatus.ACTIVE);

		// TODO: Set reservation expire date to next day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); // bugünün tarihini al
		calendar.add(Calendar.DATE, 1); // 1 gün ekle
		reservation.setExpireAt(calendar.getTime());

		reservationRepostiory.save(reservation);

		return reservationMapper.reservationToCreatedReservationResponse(reservation);
	}

	public CancelledReservationResponse cancel(int reservationId) {
		Reservation reservation = reservationBusinessRules.reservationShouldExistWithGivenId(reservationId);
		reservationBusinessRules.onlyActiveReservationsCanBeCancelled(reservation);

		reservation.setReservationStatus(ReservationStatus.CANCELLED);
		reservationRepostiory.save(reservation);

		ReservationMapper reservationMapper = ReservationMapper.INSTANCE;
		return reservationMapper.reservationToCancelledReservationResponse(reservation);
	}
}
