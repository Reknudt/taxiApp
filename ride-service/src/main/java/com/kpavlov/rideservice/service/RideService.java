package com.kpavlov.rideservice.service;

import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;

public interface RideService {

    RideResponse createRide(RideCreateRequest createRideRequest);

    RideResponse updateRide(Long id, RideUpdateRequest updateRideRequest);

    void deleteRide(Long id);

    void updateStatusCompleted(Long id);

    void updateStatusAccepted(Long id);

    void updateStatusCancelled(Long id);

    void updateStatusOnTheWayToDestination(Long id);

    void updateStatusOnTheWayToPassenger(Long id);

    RideResponse getRideById(Long id);

    RideResponsePage getAllRides(int offset, int limit);
}