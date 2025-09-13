package com.turkcell.LibraryAppDb.dto.book.response;

public class DeletedBookResponse {
	private String title;

	public DeletedBookResponse() {
	}

	public DeletedBookResponse(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
