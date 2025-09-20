package com.turkcell.LibraryAppDb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.reservation.request.CreateReservationRequest;
import com.turkcell.LibraryAppDb.dto.reservation.response.CancelledReservationResponse;
import com.turkcell.LibraryAppDb.dto.reservation.response.CreatedReservationResponse;
import com.turkcell.LibraryAppDb.service.ReservationService;

@RestController
@RequestMapping("api/")
public class ReservationController {
	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@PostMapping("v1/reservations")
	@ResponseStatus(HttpStatus.CREATED)
	public CreatedReservationResponse reserve(@RequestBody CreateReservationRequest request) {
		return reservationService.reserve(request);
	}

	@PostMapping("v1/reservations/{id}/cancel")
	public CancelledReservationResponse cancel(@PathVariable int reservationId) {
		return reservationService.cancel(reservationId);
	}
}
