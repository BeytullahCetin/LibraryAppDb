package com.turkcell.LibraryAppDb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.turkcell.LibraryAppDb.dto.fine.response.GetFineByCustomerIdResponse;
import com.turkcell.LibraryAppDb.dto.fine.response.UpdatedFineResponse;
import com.turkcell.LibraryAppDb.entity.Fine;

@Mapper(componentModel = "spring")
public interface FineMapper {
	FineMapper INSTANCE = Mappers.getMapper(FineMapper.class);

	GetFineByCustomerIdResponse fineToGetFineByCustomerId(Fine fine);

	UpdatedFineResponse fineToUpdatedFineResponse(Fine fine);
}
