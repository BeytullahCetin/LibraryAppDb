package com.turkcell.LibraryAppDb.dto.book.response;

import java.util.Date;

public class UpdatedBookResponse {
	private int id;
	private String title;
	private String isbn;
	private int pageCount;
	private Date publishDate;
	private String publisherName;
	private String languageName;

	public UpdatedBookResponse() {
	}

	public UpdatedBookResponse(int id, String title, String isbn, int pageCount, Date publishDate, String publisherName,
			String languageName) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.pageCount = pageCount;
		this.publishDate = publishDate;
		this.publisherName = publisherName;
		this.languageName = languageName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

}
