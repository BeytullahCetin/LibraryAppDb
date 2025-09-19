package com.turkcell.LibraryAppDb.dto.fine.response;

import java.util.Date;

import com.turkcell.LibraryAppDb.entity.enums.FineType;

public class UpdatedFineResponse {
	private int id;
	private float amount;
	private Date date;
	private FineType fineType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public FineType getFineType() {
		return fineType;
	}

	public void setFineType(FineType fineType) {
		this.fineType = fineType;
	}

}
