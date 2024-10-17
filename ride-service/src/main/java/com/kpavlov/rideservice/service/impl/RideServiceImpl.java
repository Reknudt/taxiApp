package com.kpavlov.rideservice.service.impl;

import static com.kpavlov.rideservice.util.ErrorMessages.ERROR_DRIVER_NOT_EXISTS;
import static com.kpavlov.rideservice.util.ErrorMessages.ERROR_PASSENGER_NOT_EXISTS;
import static com.kpavlov.rideservice.util.ErrorMessages.ERROR_NOT_FOUND;
import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
//import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
//import com.kpavlov.modulepassengerservice.repository.PassengerRepository;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;
import com.kpavlov.rideservice.exception.DuplicateFoundException;
import com.kpavlov.rideservice.exception.RideNotFoundException;
import com.kpavlov.rideservice.mapper.RideMapper;
import com.kpavlov.rideservice.model.Ride;
import com.kpavlov.rideservice.model.RideStatus;
import com.kpavlov.rideservice.repository.RideRepository;
import com.kpavlov.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
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
    private final MessageSource messageSource;
    //private final DriverRepository driverRepository;
    //private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public RideResponse createRide(RideCreateRequest createRideRequest) {
        //checkCreateRideData(createRideRequest);

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
    public void updateStatusCompleted(Long id) {
        Ride ride = findRideByIdOrThrow(id);
        ride.setStatus(RideStatus.COMPLETED);
        rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public void updateStatusAccepted(Long id) {
        Ride ride = findRideByIdOrThrow(id);
        ride.setStatus(RideStatus.ACCEPTED);
        rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public void updateStatusCancelled(Long id) {
        Ride ride = findRideByIdOrThrow(id);
        ride.setStatus(RideStatus.CANCELLED);
        rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public void updateStatusOnTheWayToDestination(Long id) {
        Ride ride = findRideByIdOrThrow(id);
        ride.setStatus(RideStatus.ON_THE_WAY_TO_DESTINATION);
        rideMapper.toResponse(rideRepository.save(ride));
    }

    @Override
    @Transactional
    public void updateStatusOnTheWayToPassenger(Long id) {
        Ride ride = findRideByIdOrThrow(id);
        ride.setStatus(RideStatus.ON_THE_WAY_TO_PASSENGER);
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
    public RideResponsePage getAllRides(int offset, int limit) {
        Page<Ride> ridePage = rideRepository.findAll(PageRequest.of(offset, limit));

        List<RideResponse> RideResponses = ridePage.getContent().stream()
                .map(ride -> new RideResponse(ride.getId(), ride.getDriverId(), ride.getPassengerId(), ride.getAddressDeparture(), ride.getAddressDestination(), ride.getStatus().name(), ride.getDate(), ride.getPrice()))
                .collect(Collectors.toList());

        return new RideResponsePage(RideResponses, ridePage.getNumber(), ridePage.getTotalPages(), ridePage.getTotalElements());
    }

//    private void checkCreateRideData(RideCreateRequest createRideRequest){
//        if (driverRepository.existsById(createRideRequest.driverId())) {
//            Long driverId = createRideRequest.driverId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_DRIVER_NOT_EXISTS,
//                    new Object[]{driverId},
//                    null));
//        }
//        if (passengerRepository.existsById(createRideRequest.passengerId())) {
//            Long passengerId = createRideRequest.passengerId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_PASSENGER_NOT_EXISTS,
//                    new Object[]{passengerId},
//                    null));
//        }
//    }
//
private void checkUpdateRideData(RideUpdateRequest updateRideRequest, Ride existingRide) {

    if (updateRideRequest.addressDestination().equals(updateRideRequest.addressDeparture())){
        String address = updateRideRequest.addressDestination();

        throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_NOT_FOUND,                        ///
                    new Object[]{address},
                   null));        }
    }

//        if (!updateRideRequest.driverId().equals(existingRide.getDriverId()) && driverRepository.existsById(updateRideRequest.driverId())) {
//            Long driverId = updateRideRequest.driverId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_DRIVER_NOT_EXISTS,
//                    new Object[]{driverId},
//                    null));        }
//        if (!updateRideRequest.passengerId().equals(existingRide.getPassengerId()) && passengerRepository.existsById(updateRideRequest.passengerId())) {
//            Long passengerId = updateRideRequest.passengerId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_PASSENGER_NOT_EXISTS,
//                    new Object[]{passengerId},
//                    null));
//        }
//    }

    private Ride findRideByIdOrThrow(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(
                        () -> new RideNotFoundException(messageSource.getMessage(
                ERROR_NOT_FOUND,
                new Object[]{id},
                null)));
    }
}