package com.turkcell.LibraryAppDb.dto.book.response;

import java.util.Date;

public class GetByIdBookResponse {
	private String title;
	private String isbn;
	private int pageCount;
	private Date publishDate;

	public GetByIdBookResponse(String title, String isbn, int pageCount, Date publishDate) {
		this.title = title;
		this.isbn = isbn;
		this.pageCount = pageCount;
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

}
