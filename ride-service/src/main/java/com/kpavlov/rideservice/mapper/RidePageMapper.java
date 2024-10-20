package com.kpavlov.rideservice.mapper;

import com.kpavlov.rideservice.dto.response.RideResponse;

import com.kpavlov.rideservice.dto.response.RideResponsePage;
import com.kpavlov.rideservice.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RidePageMapper {

    RideResponsePage toRideResponsePage(List<RideResponse> rideResponses, Page<Ride> ridePage, int limit);
}