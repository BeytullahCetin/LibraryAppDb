package com.turkcell.LibraryAppDb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.turkcell.LibraryAppDb.dto.reservation.request.CreateReservationRequest;
import com.turkcell.LibraryAppDb.dto.reservation.response.CancelledReservationResponse;
import com.turkcell.LibraryAppDb.dto.reservation.response.CreatedReservationResponse;
import com.turkcell.LibraryAppDb.entity.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
	ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

	@Mapping(source = "customerId", target = "customer.id")
	@Mapping(source = "bookId", target = "book.id")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "expireAt", ignore = true)
	@Mapping(target = "reservationStatus", ignore = true)
	Reservation createReservationRequestToReservation(CreateReservationRequest createReservationRequest);

	@Mapping(source = "customer.id", target = "customerId")
	@Mapping(source = "book.id", target = "bookId")
	CreatedReservationResponse reservationToCreatedReservationResponse(Reservation reservation);

	@Mapping(source = "customer.id", target = "customerId")
	@Mapping(source = "book.id", target = "bookId")
	CancelledReservationResponse reservationToCancelledReservationResponse(Reservation reservation);

}
