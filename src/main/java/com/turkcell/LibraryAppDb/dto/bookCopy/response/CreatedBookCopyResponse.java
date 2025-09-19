package com.turkcell.LibraryAppDb.dto.bookCopy.response;

import com.turkcell.LibraryAppDb.entity.enums.BookStatus;

import java.util.Date;

public class CreatedBookCopyResponse {
    private int id;
    private Date acquisionDate;
    private BookStatus bookStatus;
    private int bookId;

    public CreatedBookCopyResponse() {}

    public CreatedBookCopyResponse(int id, Date acquisionDate, BookStatus bookStatus, int bookId) {
        this.id = id;
        this.acquisionDate = acquisionDate;
        this.bookStatus = bookStatus;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getAcquisionDate() {
        return acquisionDate;
    }
    public void setAcquisionDate(Date acquisionDate) {
        this.acquisionDate = acquisionDate;
    }
    public BookStatus getBookStatus() {
        return bookStatus;
    }
    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
