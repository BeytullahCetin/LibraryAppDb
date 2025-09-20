package com.turkcell.LibraryAppDb.dto.fine.response;

import java.util.Date;

import com.turkcell.LibraryAppDb.entity.enums.FineType;

public class PaidFineResponse {
	private int id;
	private float amount;
	private Date issuDate;
	private Date paymentDate;
	private FineType fineType;
	private boolean isPaid;

	public PaidFineResponse() {
	}

	public PaidFineResponse(int id, float amount, Date issuDate, Date paymentDate, FineType fineType, boolean isPaid) {
		this.id = id;
		this.amount = amount;
		this.issuDate = issuDate;
		this.paymentDate = paymentDate;
		this.fineType = fineType;
		this.isPaid = isPaid;
	}

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

	public FineType getFineType() {
		return fineType;
	}

	public void setFineType(FineType fineType) {
		this.fineType = fineType;
	}

	public Date getIssuDate() {
		return issuDate;
	}

	public void setIssuDate(Date issuDate) {
		this.issuDate = issuDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}
