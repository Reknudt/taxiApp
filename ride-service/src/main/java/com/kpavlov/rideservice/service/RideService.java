package com.kpavlov.rideservice.service;

import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;
import com.kpavlov.rideservice.model.RideStatus;

public interface RideService {

    RideResponse createRide(RideCreateRequest createRideRequest);

    RideResponse updateRide(Long id, RideUpdateRequest updateRideRequest);

    void deleteRide(Long id);

    void updateStatus(Long id, RideStatus status);

    RideResponse getRideById(Long id);

    RideResponsePage getRideByStatus(RideStatus status, int offset, int limit);

    RideResponsePage getRideByPassengerId(Long id, int offset, int limit);

    RideResponsePage getRideByDriverId(Long id, int offset, int limit);

    RideResponsePage getAllRides(int offset, int limit);
}