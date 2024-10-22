package com.kpavlov.rideservice.dto.response;

import java.util.List;

public record RideResponsePage (

        List<RideResponse> rideResponses,

        int currentPage,

        int totalPages,

        int limit
) {}