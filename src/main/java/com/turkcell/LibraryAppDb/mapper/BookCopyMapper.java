package com.turkcell.LibraryAppDb.mapper;

import com.turkcell.LibraryAppDb.dto.bookCopy.request.CreateBookCopyRequest;
import com.turkcell.LibraryAppDb.dto.bookCopy.request.UpdateBookCopyRequest;
import com.turkcell.LibraryAppDb.dto.bookCopy.response.CreatedBookCopyResponse;
import com.turkcell.LibraryAppDb.dto.bookCopy.response.UpdatedBookCopyResponse;
import com.turkcell.LibraryAppDb.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyMapper INSTANCE = Mappers.getMapper(BookCopyMapper.class);

    BookCopy createBookCopyRequestToBookCopy(CreateBookCopyRequest request);

    CreatedBookCopyResponse bookCopyToCreatedBookCopyResponse(BookCopy bookCopy);

    BookCopy updateBookCopyRequestToBookCopy(UpdateBookCopyRequest request);

    UpdatedBookCopyResponse bookCopyToUpdatedBookCopyResponse(BookCopy bookCopy);
}
