package com.turkcell.LibraryAppDb.dto.customer.response;

public class DeletedCustomerResponse {
	private String name;

	public DeletedCustomerResponse() {
	}

	public DeletedCustomerResponse(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
