package com.kpavlov.rideservice.dto.response.error;

import lombok.Builder;

@Builder
public record ErrorResponse(

        int status,

        String message
) {}