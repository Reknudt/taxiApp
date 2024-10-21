package com.kpavlov.rideservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public record RideResponsePage (

        List<RideResponse> rideResponses,

        int currentPage,

        int totalPages,

        int limit
) {}