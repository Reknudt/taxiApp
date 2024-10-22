package com.kpavlov.modulepassengerservice.service;

import com.kpavlov.modulepassengerservice.dto.request.create.PassengerCreateRequest;
import com.kpavlov.modulepassengerservice.dto.request.update.PassengerUpdateRequest;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponse;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponsePage;

public interface PassengerService {

    PassengerResponse createPassenger(PassengerCreateRequest createPassengerRequest);

    PassengerResponse updatePassenger(Long passengerId, PassengerUpdateRequest updatePassengerRequest);

    void deletePassenger(Long id);

    void softDeletePassenger(Long id);

    PassengerResponse getPassengerById(Long id);

    PassengerResponsePage getAllPassengers(int offset, int limit);
}