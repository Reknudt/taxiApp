package com.kpavlov.ratingservice.mapper;

import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {

    @Mapping(target = "id", ignore = true)
    Rating createRequestToEntity(RatingCreateRequest createRatingRequest);

    void updateRatingFromUpdateRequest(RatingUpdateRequest updateRatingRequest, @MappingTarget Rating rating);

    @Mapping(target = "id")
    RatingResponse toResponse(Rating rating);
}