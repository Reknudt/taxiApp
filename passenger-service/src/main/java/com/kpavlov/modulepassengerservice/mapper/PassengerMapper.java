package com.kpavlov.modulepassengerservice.mapper;

import com.kpavlov.modulepassengerservice.dto.request.create.PassengerCreateRequest;
import com.kpavlov.modulepassengerservice.dto.request.update.PassengerUpdateRequest;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponse;
import com.kpavlov.modulepassengerservice.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    Passenger createRequestToEntity(PassengerCreateRequest createPassengerRequest);

    void updatePassengerFromUpdateRequest(PassengerUpdateRequest updatePassengerRequest, @MappingTarget Passenger passenger);

    @Mapping(target = "id")
    PassengerResponse toResponse(Passenger passenger);
}