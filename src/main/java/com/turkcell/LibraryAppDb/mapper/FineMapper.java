package com.turkcell.LibraryAppDb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.turkcell.LibraryAppDb.dto.fine.response.GetFineByCustomerIdResponse;
import com.turkcell.LibraryAppDb.dto.fine.response.PaidFineResponse;
import com.turkcell.LibraryAppDb.entity.Fine;

@Mapper(componentModel = "spring")
public interface FineMapper {
	FineMapper INSTANCE = Mappers.getMapper(FineMapper.class);

	GetFineByCustomerIdResponse fineToGetFineByCustomerId(Fine fine);

	PaidFineResponse fineToPaidFineResponse(Fine fine);
}
