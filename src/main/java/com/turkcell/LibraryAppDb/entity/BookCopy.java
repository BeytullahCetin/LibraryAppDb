package com.turkcell.LibraryAppDb.entity;

import java.util.Date;
import java.util.List;

import com.turkcell.LibraryAppDb.entity.enums.BookStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "book_copies")
public class BookCopy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date acquisionDate;
	private BookStatus bookStatus;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@OneToMany(mappedBy = "bookCopy")
	private List<Borrow> borrows;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getAcquisionDate() {
		return acquisionDate;
	}

	public void setAcquisionDate(Date acquisionDate) {
		this.acquisionDate = acquisionDate;
	}

	public List<Borrow> getBorrows() {
		return borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

}
