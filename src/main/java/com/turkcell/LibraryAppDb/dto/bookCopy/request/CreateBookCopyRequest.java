package com.turkcell.LibraryAppDb.dto.bookCopy.request;

import com.turkcell.LibraryAppDb.entity.enums.BookStatus;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CreateBookCopyRequest {
    @NotNull(message = "AcquisionDate boş olamaz")
    private Date acquisionDate;

    @NotNull(message = "BookStatus boş olamaz")
    private BookStatus bookStatus;

    @NotNull(message = "BookId boş olamaz")
    private int bookId;

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
