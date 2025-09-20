package com.turkcell.LibraryAppDb.dto.borrow.response;

public class DeletedBorrowResponse {
    private int id;
    
    public DeletedBorrowResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
