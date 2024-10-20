package com.kpavlov.rideservice.service.impl;

import static com.kpavlov.rideservice.util.ErrorMessages.ERROR_NOT_FOUND;
import static com.kpavlov.rideservice.util.ErrorMessages.ERROR_NO_WAY;

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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RidePageMapper ridePageMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public RideResponse createRide(RideCreateRequest createRideRequest) {
        checkCreateRideData(createRideRequest);

        Ride ride = rideMapper.createRequestToEntity(createRideRequest);
        ride.setStatus(RideStatus.CREATED);
        return rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public RideResponse updateRide(Long id, RideUpdateRequest updateRideRequest) {
        Ride ride = findRideByIdOrThrow(id);

        checkUpdateRideData(updateRideRequest, ride);

        rideMapper.updateRideFromUpdateRequest(updateRideRequest, ride);
        return rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public void updateStatus(Long id, RideStatus status) {
        Ride ride = findRideByIdOrThrow(id);
        ride.setStatus(status);
        rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }

    @Override
    public RideResponse getRideById(Long id) {
        Ride ride = findRideByIdOrThrow(id);
        return rideMapper.toResponse(ride);
    }

    @Override
    public RideResponsePage getRideByStatus(RideStatus status, int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAllByStatus(status, PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .collect(Collectors.toList());

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    @Override
    public RideResponsePage getRideByPassengerId(Long id, int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAllByPassengerId(id, PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .collect(Collectors.toList());

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    @Override
    public RideResponsePage getRideByDriverId(Long id, int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAllByDriverId(id, PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .collect(Collectors.toList());

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    @Override
    public RideResponsePage getAllRides(int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAll(PageRequest.of(offset, limit));

        List<RideResponse> rideResponses = ridePage.getContent().stream()
                .map(rideMapper::toResponse)
                .collect(Collectors.toList());

        return ridePageMapper.toRideResponsePage(rideResponses, ridePage, limit);
    }

    private void checkCreateRideData(RideCreateRequest createRideRequest) {}

    private void checkUpdateRideData(RideUpdateRequest updateRideRequest, Ride existingRide) {

        if (updateRideRequest.addressDestination().equals(updateRideRequest.addressDeparture())) {
            String address = updateRideRequest.addressDestination();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_NO_WAY,
                    new Object[]{address},
                    LocaleContextHolder.getLocale()));
        }
    }

    private Ride findRideByIdOrThrow(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(
                        () -> new RideNotFoundException(messageSource.getMessage(
                                ERROR_NOT_FOUND,
                                new Object[]{id},
                                LocaleContextHolder.getLocale())));
    }
}