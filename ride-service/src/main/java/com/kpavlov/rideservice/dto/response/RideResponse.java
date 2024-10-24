package com.kpavlov.rideservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideResponse (

        long id,

        long driverId,

        long passengerId,

        String addressDeparture,

        String addressDestination,

        String status,

        LocalDateTime date,

        BigDecimal price
) {}