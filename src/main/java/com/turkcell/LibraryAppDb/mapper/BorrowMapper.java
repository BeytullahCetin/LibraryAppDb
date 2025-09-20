package com.turkcell.LibraryAppDb.mapper;

import com.turkcell.LibraryAppDb.dto.borrow.request.CreateBorrowRequest;
import com.turkcell.LibraryAppDb.dto.borrow.request.UpdateBorrowRequest;
import com.turkcell.LibraryAppDb.dto.borrow.response.CreatedBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.DeletedBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.GetByIdBorrowResponse;
import com.turkcell.LibraryAppDb.dto.borrow.response.UpdatedBorrowResponse;
import com.turkcell.LibraryAppDb.entity.Borrow;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BorrowMapper {
    BorrowMapper INSTANCE = Mappers.getMapper(BorrowMapper.class);

    Borrow createBorrowRequestToBorrow(CreateBorrowRequest request);

    CreatedBorrowResponse borrowToCreatedBorrowResponse(Borrow borrow);

    Borrow updateBorrowRequestToBorrow(UpdateBorrowRequest request);

    UpdatedBorrowResponse borrowToUpdatedBorrowResponse(Borrow borrow);

    DeletedBorrowResponse borrowToDeletedBorrowResponse(Borrow borrow);

    GetByIdBorrowResponse borrowToGetByIdBorrowResponse(Borrow borrow);

    List<GetByIdBorrowResponse> borrowToGetByIdBorrowResponse(List<Borrow> borrows);
}
