package com.kpavlov.ratingservice.service;

import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.dto.response.RatingResponsePage;

public interface RatingService {

    RatingResponse createRating(RatingCreateRequest createRatingRequest);

    RatingResponse updateRating(long id, RatingUpdateRequest updateRatingRequest);

    void updateDriverRate(long id, int rate);

    void updatePassengerRate(long id, int rate);

    void deleteRating(long id);

    RatingResponse getRatingById(long id);

    RatingResponsePage findRatingsByDriverId(long id, int offset, int limit);

    RatingResponsePage findRatingsByPassengerId(long id, int offset, int limit);

    RatingResponsePage getAllRatings(int offset, int limit);

    Float getDriverRating(long id);

    Float getPassengerRating(long id);
}