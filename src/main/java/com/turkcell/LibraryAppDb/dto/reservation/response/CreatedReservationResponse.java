package com.turkcell.LibraryAppDb.dto.reservation.response;

import java.util.Date;

import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;

import jakarta.validation.constraints.Positive;

public class CreatedReservationResponse {
	private int customerId;
	private int bookId;
	private Date expireAt;
	private ReservationStatus reservationStatus;

	public CreatedReservationResponse() {
	}

	public CreatedReservationResponse(@Positive int customerId, @Positive int bookId, Date expireAt,
			ReservationStatus reservationStatus) {
		this.customerId = customerId;
		this.bookId = bookId;
		this.expireAt = expireAt;
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

	public Date getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

}
