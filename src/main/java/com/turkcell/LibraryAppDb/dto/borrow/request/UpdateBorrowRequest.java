package com.turkcell.LibraryAppDb.dto.borrow.request;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class UpdateBorrowRequest {
    @NotNull
    private Date borrDate;
    @NotNull
    private Date dueDate;
    private Date returnDate;
    @NotNull
    private int customerId;
    @NotNull
    private int bookCopyId;

    public Date getBorrDate() {
        return borrDate;
    }

    public void setBorrDate(Date borrDate) {
        this.borrDate = borrDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }
}
