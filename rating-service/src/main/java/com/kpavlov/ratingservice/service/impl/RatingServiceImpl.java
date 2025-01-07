package com.kpavlov.ratingservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.dto.response.RatingResponsePage;
import com.kpavlov.ratingservice.exception.RatingNotFoundException;
import com.kpavlov.ratingservice.exception.ResourceNotFoundException;
import com.kpavlov.ratingservice.mapper.RatingKafkaMapper;
import com.kpavlov.ratingservice.mapper.RatingMapper;
import com.kpavlov.ratingservice.mapper.RatingPageMapper;
import com.kpavlov.ratingservice.model.Rating;
import com.kpavlov.ratingservice.repository.RatingRepository;
import com.kpavlov.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kpavlov.ratingservice.util.ErrorMessages.DRIVER_NOT_FOUND;
import static com.kpavlov.ratingservice.util.ErrorMessages.ERROR_NOT_FOUND;
import static com.kpavlov.ratingservice.util.ErrorMessages.PASSENGER_NOT_FOUND;
import static com.kpavlov.ratingservice.util.ErrorMessages.RIDE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RatingPageMapper ratingPageMapper;
    private final KafkaProducer kafkaProducer;
    private final KafkaConsumer kafkaConsumer;

    @Override
    @Transactional
    public RatingResponse createRating(RatingCreateRequest createRatingRequest) {
        checkCreateRatingData(createRatingRequest);

        Rating rating = ratingMapper.createRequestToEntity(createRatingRequest);

        ratingRepository.save(rating);
        return ratingMapper.toResponse(rating);
    }

    @Override
    @Transactional
    public RatingResponse updateRating(long id, RatingUpdateRequest updateRatingRequest) {
        Rating rating = findRatingByIdOrThrow(id);

        checkUpdateRatingData(updateRatingRequest, rating);

        ratingMapper.updateRatingFromUpdateRequest(updateRatingRequest, rating);
        ratingRepository.save(rating);
        return ratingMapper.toResponse(rating);
    }

    @Override
    @Transactional
    public void updateDriverRate(long id, int rate) {
        Rating rating = findRatingByIdOrThrow(id);

        rating.setDriverRate(rate);
        ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void updatePassengerRate(long id, int rate) {
        Rating rating = findRatingByIdOrThrow(id);

        rating.setPassengerRate(rate);
        ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void deleteRating(long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public RatingResponse getRatingById(long id) {
        Rating rating = findRatingByIdOrThrow(id);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public RatingResponsePage findRatingsByDriverId(long id, int offset, int limit) {
        Page<Rating> ratingPage = ratingRepository.findAllByDriverId(id, PageRequest.of(offset, limit));

        List<RatingResponse> ratingResponses = ratingPage.getContent().stream()
                .map(ratingMapper::toResponse)
                .toList();

        return ratingPageMapper.toRatingResponsePage(ratingResponses, ratingPage, limit);
    }

    @Override
    public RatingResponsePage findRatingsByPassengerId(long id, int offset, int limit) {
        Page<Rating> ratingPage = ratingRepository.findAllByPassengerId(id, PageRequest.of(offset, limit));

        List<RatingResponse> ratingResponses = ratingPage.getContent().stream()
                .map(ratingMapper::toResponse)
                .toList();

        return ratingPageMapper.toRatingResponsePage(ratingResponses, ratingPage, limit);
    }

    @Override
    public RatingResponsePage getAllRatings(int offset, int limit) {
        Page<Rating> ratingPage = ratingRepository.findAll(PageRequest.of(offset, limit));

        List<RatingResponse> ratingResponses = ratingPage.getContent().stream()
                .map(ratingMapper::toResponse)
                .toList();

        return ratingPageMapper.toRatingResponsePage(ratingResponses, ratingPage, limit);
    }

    @Override
    public Float getDriverRating(long driverId) {
        List<Rating> ratings = ratingRepository.findAllByDriverId(driverId);

        double avg = 0;

        for(Rating rating : ratings) {
            avg += rating.getDriverRate();
        }
        return (float) avg/ratings.size();
    }

    @Override
    public Float getPassengerRating(long passengerId) {
        List<Rating> ratings = ratingRepository.findAllByDriverId(passengerId);

        double avg = 0;

        for(Rating rating : ratings) {
            avg += rating.getPassengerRate();
        }
        return (float) avg/ratings.size();
    }

    private void checkCreateRatingData(RatingCreateRequest createRatingRequest) {
        try {
            String existingDriver = String.valueOf(createRatingRequest.driverId());
            String existingRide = String.valueOf(createRatingRequest.rideId());
            String existingPassenger = String.valueOf(createRatingRequest.passengerId());

            kafkaProducer.sendMessage("driver", existingDriver);
            kafkaProducer.sendMessage("ride", existingRide);
            kafkaProducer.sendMessage("passenger", existingPassenger);

            Thread.sleep(200);

            if (!kafkaConsumer.driverExist) {
                throw new ResourceNotFoundException(DRIVER_NOT_FOUND, existingDriver);
            }
            if (!kafkaConsumer.rideExist) {
                throw new ResourceNotFoundException(RIDE_NOT_FOUND, existingRide);
            }
            if (!kafkaConsumer.passengerExist) {
                throw new ResourceNotFoundException(PASSENGER_NOT_FOUND, existingPassenger);
            }
        } catch (InterruptedException e) {}
    }

    private void checkUpdateRatingData(RatingUpdateRequest updateRatingRequest, Rating existingRating) {
        try {
            String existingDriver = String.valueOf(updateRatingRequest.driverId());
            String existingRide = String.valueOf(updateRatingRequest.rideId());
            String existingPassenger = String.valueOf(updateRatingRequest.passengerId());

            kafkaProducer.sendMessage("driver", existingDriver);
            kafkaProducer.sendMessage("ride", existingRide);
            kafkaProducer.sendMessage("passenger", existingPassenger);

            Thread.sleep(200);

            if (!kafkaConsumer.driverExist) {
                throw new ResourceNotFoundException(DRIVER_NOT_FOUND, existingDriver);
            }
            if (!kafkaConsumer.rideExist) {
                throw new ResourceNotFoundException(RIDE_NOT_FOUND, existingRide);
            }
            if (!kafkaConsumer.passengerExist) {
                throw new ResourceNotFoundException(PASSENGER_NOT_FOUND, existingPassenger);
            }
        } catch (InterruptedException e) {}
    }

    private Rating findRatingByIdOrThrow(long id) {
        return ratingRepository.findById(id)
                .orElseThrow(
                        () -> new RatingNotFoundException(ERROR_NOT_FOUND, Long.toString(id)));
    }
}