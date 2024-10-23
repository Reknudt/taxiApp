package com.kpavlov.ratingservice.mapper;

import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.dto.response.RatingResponsePage;
import com.kpavlov.ratingservice.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingPageMapper {

    RatingResponsePage toRatingResponsePage(List<RatingResponse> ratingResponses,
                                            Page<Rating> ratingPage,
                                            int limit);
}