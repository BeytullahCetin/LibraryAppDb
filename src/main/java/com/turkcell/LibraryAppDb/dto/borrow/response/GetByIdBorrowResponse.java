package com.turkcell.LibraryAppDb.dto.borrow.response;

import java.util.Date;

public class GetByIdBorrowResponse {
    private int id;
    private Date borrDate;
    private Date dueDate;
    private Date returnDate;
    private int customerId;
    private int bookCopyId;

    public GetByIdBorrowResponse() {}

    public GetByIdBorrowResponse(int id, Date borrDate, Date dueDate, Date returnDate, int customerId, int bookCopyId) {
        this.id = id;
        this.borrDate = borrDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.customerId = customerId;
        this.bookCopyId = bookCopyId;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
