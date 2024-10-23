package com.kpavlov.ratingservice.dto.request.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingCreateRequest(

        @NotNull(message = "{field.required}")
        long driverId,

        @NotNull(message = "{field.required}")
        long passengerId,

        @NotNull(message = "{field.required}")
        long rideId,

        @NotNull(message = "{field.required}")
        @Min(value = 1, message = "{field.toosmall}")
        @Max(value = 5, message = "{field.toobig}")
        int driverRate,

        @NotNull(message = "{field.required}")
        @Min(value = 1, message = "{field.toosmall}")
        @Max(value = 5, message = "{field.toobig}")
        int passengerRate,

        String driverComment,

        String passengerComment
) {}