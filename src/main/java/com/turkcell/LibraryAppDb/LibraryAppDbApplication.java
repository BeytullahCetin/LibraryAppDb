package com.turkcell.LibraryAppDb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryAppDbApplication {

	// TODO: 1
	/*
	 * - DTO - Mapper
	 * BookCopy, Borrow(Alişan)
	 * Fine, Reservation(Beytullah)
	 * - REPOSITRY - Rules
	 * Book, Borrow, Customer(Hakkı)
	 * Reservation, Fine(Beytullah)
	 * - SERVICE - Controller
	 * Customer, Book, Borrow (Hakkı)
	 * Reservation, Fine(Beytullah)
	 */

	// 2 entity için dto-service-controller
	// yapısını koruyarak add ve getAll methodlarını tasarlamak.

	// -------

	// Ana tablolar için:
	// Listeleme
	// ID'ye göre getirme
	// ekleme
	// silme
	// güncelleme
	// CRUD

	// -------

	// En az 2 entity icin custom query yazilacak. (Plan bizde)
	// Her istekte requestleri validasyon kurallariyla koruyalim.

	// -------

	// 1 tane servis'e automapper entegre et.

	// --------

	//

	public static void main(String[] args) {
		SpringApplication.run(LibraryAppDbApplication.class, args);
	}

}
