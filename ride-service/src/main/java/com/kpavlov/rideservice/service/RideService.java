package com.kpavlov.rideservice.service;

import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;
import com.kpavlov.rideservice.model.RideStatus;

public interface RideService {

    RideResponse createRide(RideCreateRequest createRideRequest);

    RideResponse updateRide(long id, RideUpdateRequest updateRideRequest);

    void deleteRide(long id);

    void softDeleteRide(long id);

    void updateStatus(long id, RideStatus status);

    RideResponse getRideById(long id);

    RideResponsePage findRidesByStatus(RideStatus status, int offset, int limit);

    RideResponsePage findRidesByPassengerId(long id, int offset, int limit);

    RideResponsePage findRidesByDriverId(long id, int offset, int limit);

    RideResponsePage getAllRides(int offset, int limit);
}