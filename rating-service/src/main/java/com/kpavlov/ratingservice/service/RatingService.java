package com.kpavlov.ratingservice.service;

import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.dto.response.RatingResponsePage;

public interface RatingService {

    RatingResponse createRating(RatingCreateRequest createRatingRequest);

    RatingResponse updateRating(Long id, RatingUpdateRequest updateRatingRequest);

    void updateDriverRate(Long id, int rate);

    void updatePassengerRate(Long id, int rate);

    void deleteRating(Long id);

    RatingResponse getRatingById(Long id);

    RatingResponsePage getRatingByDriverId(Long id);

    RatingResponsePage getRatingByPassengerId(Long id);

    RatingResponsePage getAllRatings(int offset, int limit);
}