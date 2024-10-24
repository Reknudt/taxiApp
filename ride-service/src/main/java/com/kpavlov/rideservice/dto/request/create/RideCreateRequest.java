package com.kpavlov.rideservice.dto.request.create;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideCreateRequest(

        @NotNull(message = "{field.required}")
        long driverId,

        @NotNull(message = "{field.required}")
        long passengerId,

        @NotBlank(message = "{field.required}")
        String addressDeparture,

        @NotBlank(message = "{field.required}")
        String addressDestination,

        @NotNull(message = "{field.required}")
        @Future(message = "{field.future}")
        LocalDateTime date,

        @NotNull(message = "{field.required}")
        @Positive(message = "{field.positive}")
        BigDecimal price
) {}