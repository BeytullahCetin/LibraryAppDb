package com.turkcell.LibraryAppDb.dto.reservation.response;

import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;

public class CancelledReservationResponse {
	private int customerId;
	private int bookId;
	private ReservationStatus reservationStatus;

	public CancelledReservationResponse() {
	}

	public CancelledReservationResponse(int customerId, int bookId, ReservationStatus reservationStatus) {
		this.customerId = customerId;
		this.bookId = bookId;
		this.reservationStatus = reservationStatus;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

}
