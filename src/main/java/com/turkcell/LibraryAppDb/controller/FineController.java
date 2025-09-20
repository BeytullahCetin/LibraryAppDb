package com.turkcell.LibraryAppDb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.LibraryAppDb.dto.fine.response.GetFineByCustomerIdResponse;
import com.turkcell.LibraryAppDb.dto.fine.response.PaidFineResponse;
import com.turkcell.LibraryAppDb.service.FineService;

@RestController
@RequestMapping("api/")
public class FineController {
	private final FineService fineService;

	public FineController(FineService fineService) {
		this.fineService = fineService;
	}

	@GetMapping("v1/fines/customers/{customerId}")
	public List<GetFineByCustomerIdResponse> getCustemerFines(@PathVariable int customerId) {
		return fineService.getFinesByCustomerId(customerId);
	}

	@PostMapping("v1/fines/{id}/pay")
	public PaidFineResponse payFine(@PathVariable int id) {
		return fineService.payFineByFineId(id);
	}
}
