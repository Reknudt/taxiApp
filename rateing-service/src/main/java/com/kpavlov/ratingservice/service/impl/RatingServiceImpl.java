package com.kpavlov.ratingservice.service.impl;

import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.dto.response.RatingResponsePage;
import com.kpavlov.ratingservice.exception.RatingNotFoundException;
import com.kpavlov.ratingservice.mapper.RatingMapper;
import com.kpavlov.ratingservice.model.Rating;
import com.kpavlov.ratingservice.repository.RatingRepository;
import com.kpavlov.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kpavlov.ratingservice.util.ErrorMessages.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final MessageSource messageSource;
    //private final DriverRepository driverRepository;
    //private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public RatingResponse createRating(RatingCreateRequest createRatingRequest) {
        //checkCreateRatingData(createRatingRequest);

        Rating rating = ratingMapper.createRequestToEntity(createRatingRequest);

        return ratingMapper.toResponse(ratingRepository.save(rating));
    }

    @Override
    @Transactional
    public RatingResponse updateRating(Long id, RatingUpdateRequest updateRatingRequest) {
        Rating rating = findRatingByIdOrThrow(id);

        //checkUpdateRatingData(updateRatingRequest, Rating);

        ratingMapper.updateRatingFromUpdateRequest(updateRatingRequest, rating);
        return ratingMapper.toResponse(ratingRepository.save(rating));
    }

    @Override
    @Transactional
    public RatingResponse updateRate(Long id, int rate) {
        Rating rating = findRatingByIdOrThrow(id);

        rating.setRate(rate);
        return ratingMapper.toResponse(ratingRepository.save(rating));
    }

    @Override
    @Transactional
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public RatingResponse getRatingById(Long id) {
        Rating rating = findRatingByIdOrThrow(id);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public RatingResponsePage getAllRatings(int offset, int limit) {
        Page<Rating> ratingPage = ratingRepository.findAll(PageRequest.of(offset, limit));

        List<RatingResponse> ratingResponse = ratingPage.getContent().stream()
                .map(rating -> new RatingResponse(rating.getId(), rating.getDriverId(), rating.getPassengerId(), rating.getRate(), rating.getComment()))
                .collect(Collectors.toList());

        return new RatingResponsePage(ratingResponse, ratingPage.getNumber(), ratingPage.getTotalPages(), ratingPage.getTotalElements());
    }

//    private void checkCreateRatingData(RatingCreateRequest createRatingRequest){
//        if (driverRepository.existsById(createRatingRequest.driverId())) {
//            Long driverId = createRatingRequest.driverId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_DRIVER_NOT_EXISTS,
//                    new Object[]{driverId},
//                    null));
//        }
//        if (passengerRepository.existsById(createRatingRequest.passengerId())) {
//            Long passengerId = createRatingRequest.passengerId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_PASSENGER_NOT_EXISTS,
//                    new Object[]{passengerId},
//                    null));
//        }
//    }
//
//    private void checkUpdateRatingData(RatingUpdateRequest updateRatingRequest, Rating existingRating) {
//        if (!updateRatingRequest.driverId().equals(existingRating.getDriverId()) && driverRepository.existsById(updateRatingRequest.driverId())) {
//            Long driverId = updateRatingRequest.driverId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_DRIVER_NOT_EXISTS,
//                    new Object[]{driverId},
//                    null));        }
//        if (!updateRatingRequest.passengerId().equals(existingRating.getPassengerId()) && passengerRepository.existsById(updateRatingRequest.passengerId())) {
//            Long passengerId = updateRatingRequest.passengerId();
//
//            throw new DuplicateFoundException(messageSource.getMessage(
//                    ERROR_PASSENGER_NOT_EXISTS,
//                    new Object[]{passengerId},
//                    null));
//        }
//    }

    private Rating findRatingByIdOrThrow(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(
                        () -> new RatingNotFoundException(messageSource.getMessage(
                ERROR_NOT_FOUND,
                new Object[]{id},
                null)));
    }
}