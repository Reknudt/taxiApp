package com.kpavlov.rideservice.service.impl;

import com.kpavlov.rideservice.controller.ControllerAdvice;
import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;
import com.kpavlov.rideservice.exception.DuplicateFoundException;
import com.kpavlov.rideservice.exception.RideNotFoundException;
import com.kpavlov.rideservice.mapper.RideMapper;
import com.kpavlov.rideservice.mapper.RidePageMapper;
import com.kpavlov.rideservice.model.Ride;
import com.kpavlov.rideservice.model.RideStatus;
import com.kpavlov.rideservice.repository.RideRepository;
import com.kpavlov.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kpavlov.rideservice.util.HttpErrorMessages.ERROR_NOT_FOUND;
import static com.kpavlov.rideservice.util.HttpErrorMessages.ERROR_NO_WAY;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RidePageMapper ridePageMapper;
    private final ControllerAdvice controllerAdvice;

    @Override
    @Transactional
    public RideResponse createRide(RideCreateRequest createRideRequest) {
        checkCreateRideData(createRideRequest);

        Ride ride = rideMapper.createRequestToEntity(createRideRequest);
        ride.setStatus(RideStatus.CREATED);
        rideRepository.save(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse updateRide(long id, RideUpdateRequest updateRideRequest) {
        Ride ride = findRideById(id);

        checkUpdateRideData(updateRideRequest, ride);

        rideMapper.updateRideFromUpdateRequest(updateRideRequest, ride);
        rideRepository.save(ride);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public void updateStatus(long id, RideStatus status) {
        Ride ride = findRideById(id);
        ride.setStatus(status);
        rideRepository.save(ride);
        rideMapper.toResponse(ride);
    }

    @Override
    public void deleteRide(long id) {
        rideRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void softDeleteRide(long id) {
        Ride ride = findRideById(id);
        ride.setStatus(RideStatus.DELETED);
        rideRepository.save(ride);
        rideMapper.toResponse(ride);
    }

    @Override
    public RideResponse getRideById(long id) {
        Ride ride = findRideById(id);
        return rideMapper.toResponse(ride);
    }

    @Override
    public RideResponsePage findRidesByStatus(RideStatus status, int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAllByStatus(status, PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .toList();

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    @Override
    public RideResponsePage findRidesByPassengerId(long id, int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAllByPassengerId(id, PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .toList();

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    @Override
    public RideResponsePage findRidesByDriverId(long id, int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAllByDriverId(id, PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .toList();

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    @Override
    public RideResponsePage getAllRides(int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAll(PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .toList();

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    private void checkCreateRideData(RideCreateRequest createRideRequest) {

        String destination = createRideRequest.addressDestination();
        String departure = createRideRequest.addressDeparture();

        if (destination.equals(departure)) {
            throw new DuplicateFoundException(ERROR_NO_WAY);
        }
    }

    private void checkUpdateRideData(RideUpdateRequest updateRideRequest, Ride existingRide) {

        String destination = updateRideRequest.addressDestination();
        String departure = updateRideRequest.addressDeparture();

        if (destination.equals(departure)) {
            throw new DuplicateFoundException(ERROR_NO_WAY);
        }
    }

    private Ride findRideById(long id) {
        return rideRepository.findById(id)
                .orElseThrow(
                        () -> new RideNotFoundException(ERROR_NOT_FOUND, Long.toString(id)));
    }
}