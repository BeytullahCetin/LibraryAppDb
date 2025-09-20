package com.turkcell.LibraryAppDb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
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
import com.turkcell.LibraryAppDb.rules.BookBusinessRules;
import com.turkcell.LibraryAppDb.rules.ReservationBusinessRules;

import jakarta.validation.Valid;

@Service
@Validated
public class ReservationService {

	private final ReservationRepostiory reservationRepostiory;
	private final ReservationBusinessRules reservationBusinessRules;
	private final CustomerService customerService;
	private final BookBusinessRules bookBusinessRules;
	private final BookCopyService bookCopyService;

	public ReservationService(ReservationRepostiory reservationRepostiory,
			ReservationBusinessRules reservationBusinessRules,
			CustomerService customerService,
			BookBusinessRules bookBusinessRules,
			BookCopyService bookCopyService) {
		this.reservationRepostiory = reservationRepostiory;
		this.reservationBusinessRules = reservationBusinessRules;
		this.customerService = customerService;
		this.bookBusinessRules = bookBusinessRules;
		this.bookCopyService = bookCopyService;

	}

	@Scheduled(fixedRate = 6000)
	public void cancelExpiredReservations() {
		// log.info("The time is now {}", dateFormat.format(new Date()));
		// TODO: refactor with a query
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

		Customer customer = customerService.getCustomerById(request.getCustomerId());
		customer.setId(request.getCustomerId());

		Book book = bookBusinessRules.bookShouldExistWithGivenId(request.getBookId());

		long availableCopies = bookCopyService.countAvailableByBookId(book.getId());
		if (availableCopies > 0) {
			throw new IllegalArgumentException("Kitap şu anda müsait. Lütfen ödünç alma işlemi yapın.");
		}

		reservationBusinessRules.customerCannotHaveMultipleReservationsForSameBook(customer.getId(), book.getId());

		Reservation reservation = reservationMapper.createReservationRequestToReservation(request);
		reservation.setReservationStatus(ReservationStatus.ACTIVE);

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
