package com.kpavlov.ratingservice.dto.response;

public record RatingResponse(

        long id,

        long driverId,

        long passengerId,

        long rideId,

        int driverRate,

        int passengerRate,

        String driverComment,

        String passengerComment
) {}