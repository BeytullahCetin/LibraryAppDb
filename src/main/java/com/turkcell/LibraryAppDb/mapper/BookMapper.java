package com.turkcell.LibraryAppDb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.turkcell.LibraryAppDb.dto.book.request.CreateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.response.CreatedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.DeletedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.GetByIdBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.UpdatedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.UpdatedBookResponseWithCopyCount;
import com.turkcell.LibraryAppDb.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

	Book createBookRequestToBook(CreateBookRequest createBookRequest);

	CreatedBookResponse bookToCreatedBookResponse(Book book);

	UpdatedBookResponse bookToUpdatedBookResponse(Book book);

	GetByIdBookResponse bookToGetByIdBookResponse(Book book);

	DeletedBookResponse bookToDeletedBookResponse(Book book);

	UpdatedBookResponseWithCopyCount bookToUpdatedBookResponseWithCopyCount(Book book);
}
