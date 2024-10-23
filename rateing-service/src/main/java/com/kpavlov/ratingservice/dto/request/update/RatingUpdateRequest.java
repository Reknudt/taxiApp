package com.kpavlov.ratingservice.dto.request.update;

import jakarta.validation.constraints.*;

public record RatingUpdateRequest(

        @NotNull(message = "{field.required}")
        Long driverId,

        @NotNull(message = "{field.required}")
        Long passengerId,

        @NotNull(message = "{field.required}")
        @Min(value = 1, message = "{field.toosmall}")
        @Max(value = 5, message = "{field.toobig}")
        Integer rate,

        String comment
) {}