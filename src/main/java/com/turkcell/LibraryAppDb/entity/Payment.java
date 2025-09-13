package com.turkcell.LibraryAppDb.entity;

import java.util.Date;

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
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private float amount;
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "fine_id")
	private Fine fine;

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

	public Fine getFine() {
		return fine;
	}

	public void setFine(Fine fine) {
		this.fine = fine;
	}

}
