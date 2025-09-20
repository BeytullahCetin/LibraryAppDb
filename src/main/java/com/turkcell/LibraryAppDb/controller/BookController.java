package com.turkcell.LibraryAppDb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.book.request.CreateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.request.UpdateBookRequest;
import com.turkcell.LibraryAppDb.dto.book.response.CreatedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.DeletedBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.GetByIdBookResponse;
import com.turkcell.LibraryAppDb.dto.book.response.UpdatedBookResponse;
import com.turkcell.LibraryAppDb.service.BookService;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CreatedBookResponse add(@RequestBody CreateBookRequest bookDto) {
		return bookService.add(bookDto);
	}

	@GetMapping("{id}")
	public GetByIdBookResponse getById(@PathVariable int id) {
		return bookService.getById(id);
	}

	@PutMapping("{id}")
	public UpdatedBookResponse update(@PathVariable int id, @RequestBody UpdateBookRequest bookDto) {
		return bookService.update(id, bookDto);
	}

	@DeleteMapping("{id}")
	public DeletedBookResponse delete(@PathVariable int id) {
		return bookService.delete(id);
	}

	@PatchMapping("{id}/copies")
	public UpdatedBookResponse updateCopies(@PathVariable int id, @RequestParam int delta) {
		return bookService.updateCopies(id, delta);
	}

	@GetMapping(params = { "isbn", "title", "author", "available" })
	public List<GetByIdBookResponse> getByFilters(
			@RequestParam(required = false) String isbn,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String author,
			@RequestParam(required = false, defaultValue = "false") boolean available) {
		return bookService.search(isbn, title, author, available);
	}
}
