package com.kpavlov.ratingservice.dto.response;

import java.util.List;

public record RatingResponsePage (

        List<RatingResponse> ratingResponses,

        int currentPage,

        int totalPages,

        long totalElements
) {}