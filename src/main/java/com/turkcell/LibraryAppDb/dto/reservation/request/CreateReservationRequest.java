package com.turkcell.LibraryAppDb.dto.reservation.request;

import jakarta.validation.constraints.Positive;

public class CreateReservationRequest {
	@Positive
	private int customerId;
	@Positive
	private int bookId;

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
}
