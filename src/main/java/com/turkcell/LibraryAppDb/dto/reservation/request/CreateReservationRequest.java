package com.turkcell.LibraryAppDb.dto.reservation.request;

import java.util.Date;

import com.turkcell.LibraryAppDb.entity.enums.ReservationStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Positive;

public class CreateReservationRequest {
	@Positive
	private int customerId;
	@Positive
	private int bookId;
	@Temporal(TemporalType.DATE)
	private Date expireAt;
	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus;

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
