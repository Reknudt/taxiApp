package com.kpavlov.modulepassengerservice.mapper;

import com.kpavlov.modulepassengerservice.dto.response.PassengerResponse;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponsePage;
import com.kpavlov.modulepassengerservice.model.Passenger;
import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerPageMapper {

    PassengerResponsePage toPassengerResponsePage(List<PassengerResponse> passengerResponses,
                                                  Page<Passenger> passengerPage,
                                                  int limit);
}