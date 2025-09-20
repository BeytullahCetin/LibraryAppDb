package com.turkcell.LibraryAppDb.dto.book.response;

import java.util.Date;

public class UpdatedBookResponseWithCopyCount extends UpdatedBookResponse {
	private int copyCount;

	public UpdatedBookResponseWithCopyCount() {
	}

	public UpdatedBookResponseWithCopyCount(int id, String title, String isbn, int pageCount,
			Date publishDate,
			String publisherName, String languageName, int copyCount) {
		super(id, title, isbn, pageCount, publishDate, publisherName, languageName);
		this.copyCount = copyCount;
	}

	public int getCopyCount() {
		return copyCount;
	}

	public void setCopyCount(int copyCount) {
		this.copyCount = copyCount;
	}

}
