package com.turkcell.LibraryAppDb.service;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.turkcell.LibraryAppDb.dto.book.request.CreateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.request.UpdateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.response.CreatedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.DeletedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.GetByIdBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.UpdatedBookResponse;
import com.turkcell.LibraryAppDb.entity.Book;
import com.turkcell.LibraryAppDb.entity.Language;
import com.turkcell.LibraryAppDb.entity.Publisher;
import com.turkcell.LibraryAppDb.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository bookRepository;
	private final LanguageService languageService;
	private final PublisherService publisherService;

	public BookService(BookRepository bookRepository, LanguageService languageService,
			PublisherService publisherService) {
		this.bookRepository = bookRepository;
		this.languageService = languageService;
		this.publisherService = publisherService;
	}

	public CreatedBookResponse add(CreateBookRequest bookDto) {
		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		book.setIsbn(bookDto.getIsbn());
		book.setPageCount(bookDto.getPageCount());
		book.setPublishDate(bookDto.getPublishDate());

		Language language = languageService
				.findById(bookDto.getLanguageId())
				.orElseThrow(() -> new NotFoundException("Bu id ile language bulunamadi."));
		book.setLanguage(language);

		Publisher publisher = publisherService
				.findById(bookDto.getPublisherId())
				.orElseThrow(() -> new NotFoundException("Bu id ile publisher bulunamadi."));
		book.setPublisher(publisher);

		bookRepository.save(book);
		return new CreatedBookResponse(book.getTitle(), book.getIsbn(), book.getPageCount(), book.getPublishDate(),
				publisher.getName(), language.getName());
	}

	public GetByIdBookResponse getById(int id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Bu id ile book bulunamadı"));

		return new GetByIdBookResponse(book.getTitle(), book.getIsbn(), book.getPageCount(), book.getPublishDate());
	}

	public UpdatedBookResponse update(int id, UpdateBookRequest bookDto) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Bu id ile book bulunamadı"));

		Language language = languageService
				.findById(bookDto.getLanguageId())
				.orElseThrow(() -> new NotFoundException("Bu id ile language bulunamadi."));

		Publisher publisher = publisherService
				.findById(bookDto.getPublisherId())
				.orElseThrow(() -> new NotFoundException("Bu id ile publisher bulunamadi."));

		book.setTitle(bookDto.getTitle());
		book.setIsbn(bookDto.getIsbn());
		book.setPageCount(bookDto.getPageCount());
		book.setPublishDate(bookDto.getPublishDate());
		book.setLanguage(language);
		book.setPublisher(publisher);
		bookRepository.save(book);

		return new UpdatedBookResponse(book.getId(), book.getTitle(), book.getIsbn(), book.getPageCount(),
				book.getPublishDate(),
				publisher.getName(), language.getName());
	}

	public DeletedBookResponse delete(int id) {
		Book book = bookRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Bu id ile book bulunamadı"));
		bookRepository.deleteById(id);
		return new DeletedBookResponse(book.getTitle());
	}

}
