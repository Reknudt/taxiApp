package com.kpavlov.rideservice.dto.request.update;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RideUpdateRequest(

        @NotNull(message = "{field.required}")
        Long driverId,

        @NotNull(message = "{field.required}")
        Long passengerId,

        @NotBlank(message = "{field.required}")
        String addressDeparture,

        @NotBlank(message = "{field.required}")
        String addressDestination,

        @NotNull(message = "{field.required}")
        @Future(message = "{field.future}")
        LocalDateTime date,

        @NotBlank(message = "{field.required}")
        @Positive(message = "{field.positive}")
        BigDecimal price
) {}