package com.kpavlov.ratingservice.dto;

public record KafkaMessage(

        long driverId,

        long passengerId,

        long rideId
) {}