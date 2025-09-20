package com.turkcell.LibraryAppDb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.turkcell.LibraryAppDb.dto.book.request.CreateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.request.UpdateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.response.CreatedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.DeletedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.GetByIdBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.UpdatedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.UpdatedBookResponseWithCopyCount;
import com.turkcell.LibraryAppDb.entity.Book;
import com.turkcell.LibraryAppDb.entity.Language;
import com.turkcell.LibraryAppDb.entity.Publisher;
import com.turkcell.LibraryAppDb.mapper.BookMapper;
import com.turkcell.LibraryAppDb.repository.BookRepository;
import com.turkcell.LibraryAppDb.rules.BookBusinessRules;

import jakarta.validation.Valid;

@Service
@Validated
public class BookService {
	private final BookRepository bookRepository; // yalnızca bu repository
	private final LanguageService languageService;
	private final PublisherService publisherService;
	private final BookBusinessRules bookBusinessRules;
	private final BookCopyService bookCopyService;

	public BookService(BookRepository bookRepository,
			LanguageService languageService,
			PublisherService publisherService,
			BookBusinessRules bookBusinessRules,
			BookCopyService bookCopyService) {
		this.bookRepository = bookRepository;
		this.languageService = languageService;
		this.publisherService = publisherService;
		this.bookBusinessRules = bookBusinessRules;
		this.bookCopyService = bookCopyService;
	}

	public CreatedBookResponse add(@Valid CreateBookRequest bookDto) {
		bookBusinessRules.ensureIsbnUnique(bookDto.getIsbn());

		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		book.setIsbn(bookDto.getIsbn());
		book.setPageCount(bookDto.getPageCount());
		book.setPublishDate(bookDto.getPublishDate());

		Language language = languageService
				.findById(bookDto.getLanguageId())
				.orElseThrow(() -> new IllegalArgumentException("Dil bulunamadı."));
		Publisher publisher = publisherService
				.findById(bookDto.getPublisherId())
				.orElseThrow(() -> new IllegalArgumentException("Yayınevi bulunamadı."));

		bookRepository.save(book);
		return new CreatedBookResponse(book.getTitle(), book.getIsbn(), book.getPageCount(), book.getPublishDate(),
				publisher.getName(), language.getName());
	}

	public GetByIdBookResponse getById(int id) {
		Book book = bookBusinessRules.bookShouldExistWithGivenId(id);
		return new GetByIdBookResponse(book.getTitle(), book.getIsbn(), book.getPageCount(), book.getPublishDate());
	}

	public UpdatedBookResponse update(int id, @Valid UpdateBookRequest bookDto) {
		Book book = bookBusinessRules.bookShouldExistWithGivenId(id);

		Language language = languageService
				.findById(bookDto.getLanguageId())
				.orElseThrow(() -> new IllegalArgumentException("Dil bulunamadı."));
		Publisher publisher = publisherService
				.findById(bookDto.getPublisherId())
				.orElseThrow(() -> new IllegalArgumentException("Yayınevi bulunamadı."));

		book.setTitle(bookDto.getTitle());
		book.setIsbn(bookDto.getIsbn());
		book.setPageCount(bookDto.getPageCount());
		book.setPublishDate(bookDto.getPublishDate());
		book.setLanguage(language);
		book.setPublisher(publisher);
		bookRepository.save(book);

		return new UpdatedBookResponse(book.getId(), book.getTitle(), book.getIsbn(), book.getPageCount(),
				book.getPublishDate(), publisher.getName(), language.getName());
	}

	public DeletedBookResponse delete(int id) {
		Book book = bookBusinessRules.bookShouldExistWithGivenId(id);
		bookRepository.deleteById(id);
		return new DeletedBookResponse(book.getTitle());
	}

	// PATCH /api/books/{id}/copies?delta=...
	public UpdatedBookResponseWithCopyCount updateCopies(int id, int delta) {
		Book book = bookBusinessRules.bookShouldExistWithGivenId(id);

		bookBusinessRules.bookCopyDeltaMustNotBeZero(delta);

		if (delta > 0) {
			bookCopyService.createCopies(book, delta);
		} else {
			int removeCount = Math.abs(delta);
			bookBusinessRules.ensureCanRemoveCopies(id, removeCount);
			bookCopyService.removeAvailableCopies(id, removeCount);
		}

		BookMapper bookMapper = BookMapper.INSTANCE;
		UpdatedBookResponseWithCopyCount response = bookMapper.bookToUpdatedBookResponseWithCopyCount(book);
		response.setCopyCount(book.getBookCopies().size());
		return response;
	}

	// GET /api/books?isbn=&title=&author=&available=true
	public List<GetByIdBookResponse> search(String isbn, String title, String author, Boolean available) {
		List<Book> books = bookRepository.findAll();

		if (isbn != null && !isbn.isBlank()) {
			books = books.stream()
					.filter(b -> b.getIsbn() != null && b.getIsbn().toLowerCase().contains(isbn.toLowerCase()))
					.collect(Collectors.toList());
		}
		if (title != null && !title.isBlank()) {
			books = books.stream()
					.filter(b -> b.getTitle() != null && b.getTitle().toLowerCase().contains(title.toLowerCase()))
					.collect(Collectors.toList());
		}
		if (author != null && !author.isBlank()) {
			books = books.stream()
					.filter(b -> b.getAuthors() != null && b.getAuthors().stream()
							.anyMatch(a -> a.getName() != null
									&& a.getName().toLowerCase().contains(author.toLowerCase())))
					.collect(Collectors.toList());
		}
		if (available != null) {
			final boolean wantAvailable = available;
			books = books.stream().filter(b -> {
				long avail = bookCopyService.countAvailableByBookId(b.getId());
				return wantAvailable ? (avail > 0) : (avail == 0);
			}).collect(Collectors.toList());
		}

		return books.stream()
				.map(b -> new GetByIdBookResponse(b.getTitle(), b.getIsbn(), b.getPageCount(), b.getPublishDate()))
				.collect(Collectors.toList());
	}
}
