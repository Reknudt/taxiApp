package com.kpavlov.ratingservice.dto.response;

public record RatingResponse(

        Long id,

        Long driverId,

        Long passengerId,

        Integer rate,

        String comment
) {}