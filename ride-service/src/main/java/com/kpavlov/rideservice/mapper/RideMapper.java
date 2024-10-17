package com.kpavlov.rideservice.mapper;

import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {

    @Mapping(target = "id", ignore = true)
    Ride createRequestToEntity(RideCreateRequest createRideRequest);

    void updateRideFromUpdateRequest(RideUpdateRequest updateRideRequest, @MappingTarget Ride ride);

    @Mapping(target = "id")
    RideResponse toResponse(Ride ride);
}