package com.turkcell.LibraryAppDb.entity;

import java.util.Date;

import com.turkcell.LibraryAppDb.entity.enums.FineType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "fines")
public class Fine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float amount;

	@Temporal(TemporalType.DATE)
	private Date issueDate;

	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	private FineType fineType;
	private boolean isPaid;

	@ManyToOne
	@JoinColumn(name = "borrow_id")
	private Borrow borrow;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public FineType getFineType() {
		return fineType;
	}

	public void setFineType(FineType fineType) {
		this.fineType = fineType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public boolean isIsPaid() {
		return isPaid;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
