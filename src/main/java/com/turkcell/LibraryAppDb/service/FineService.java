package com.turkcell.LibraryAppDb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.LibraryAppDb.dto.fine.response.GetFineByCustomerIdResponse;
import com.turkcell.LibraryAppDb.dto.fine.response.PaidFineResponse;
import com.turkcell.LibraryAppDb.entity.Borrow;
import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.Fine;
import com.turkcell.LibraryAppDb.entity.enums.FineType;
import com.turkcell.LibraryAppDb.mapper.FineMapper;
import com.turkcell.LibraryAppDb.repository.FineRepository;
import com.turkcell.LibraryAppDb.rules.FineBusinessRules;

@Service
public class FineService {
	private final FineRepository fineRepository;
	private final FineBusinessRules fineBusinessRules;

	public FineService(FineRepository fineRepository, FineBusinessRules fineBusinessRules) {
		this.fineRepository = fineRepository;
		this.fineBusinessRules = fineBusinessRules;
	}

	public Fine createFine(Borrow borrow, FineType fineType) {
		Fine fine = new Fine();
		fine.setBorrow(borrow);
		fine.setIssueDate(new Date());
		fine.setFineType(fineType);
		fine.setAmount(
				fineBusinessRules.calculateFineAmount(borrow.getDueDate(), borrow.getReturnDate(), fine.getFineType()));

		return fineRepository.save(fine);
	}

	public PaidFineResponse payFineByFineId(int fineId) {
		FineMapper fineMapper = FineMapper.INSTANCE;

		Fine fine = fineBusinessRules.fineShouldExistWithGivenId(fineId);
		fine.setPaid(true);
		fine.setPaymentDate(new Date());
		fineRepository.save(fine);

		return fineMapper.fineToPaidFineResponse(fine);
	}

	public List<GetFineByCustomerIdResponse> getFinesByCustomerId(int customerId) {
		// TODO Check customer exists
		// Customer customer = customerBusinessRules.checkIfCustomerExists(customerId);
		Customer customer = new Customer();

		FineMapper fineMapper = FineMapper.INSTANCE;

		List<Fine> finesList = fineRepository.findByBorrow_Customer_Id(customerId);
		List<GetFineByCustomerIdResponse> fines = new ArrayList<>();

		for (Fine fine : finesList) {
			fines.add(fineMapper.fineToGetFineByCustomerId(fine));
		}

		return fines;

	}
}
