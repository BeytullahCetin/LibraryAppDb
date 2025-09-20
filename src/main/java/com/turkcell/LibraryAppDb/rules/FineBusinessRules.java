package com.turkcell.LibraryAppDb.rules;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.turkcell.LibraryAppDb.entity.Fine;
import com.turkcell.LibraryAppDb.entity.enums.FineType;
import com.turkcell.LibraryAppDb.repository.FineRepository;

@Component
public class FineBusinessRules {
	private final FineRepository fineRepository;

	public FineBusinessRules(FineRepository fineRepository) {
		this.fineRepository = fineRepository;
	}

	public Fine fineShouldExistWithGivenId(int fineId) {
		return fineRepository.findById(fineId).orElseThrow(() -> new IllegalArgumentException("Fine not found"));
	}

	public float calculateFineAmount(Date dueDate, Date returnDate, FineType fineType) {

		switch (fineType) {
			case LATE -> {
				float DAILY_RATE = 5.0f;

				// LocalDate'e çevir
				LocalDate localDueDate = dueDate.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();

				LocalDate localReturnDate = returnDate.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();

				// Gün farkını hesapla
				long lateDayCount = ChronoUnit.DAYS.between(localDueDate, localReturnDate);
				float fineAmount = lateDayCount * DAILY_RATE;

				return fineAmount;
			}
			case DAMAGE -> {
				float DAMAGE_FEE = 50.0f;
				return DAMAGE_FEE;
			}
			case LOST -> {
				float LOST_FEE = 100.0f;
				return LOST_FEE;
			}
			default -> throw new IllegalArgumentException("Invalid fine type");
		}
	}

	public void customerCannotBorrowOrReservationWhenHasUnpaidFine(int customerId) {
		if (fineRepository.existsByBorrow_Customer_IdAndIsPaid(customerId, false)) {
			throw new IllegalStateException(
					"Customer has unpaid fines. Please settle them before making a reservation.");
		}
	}
}