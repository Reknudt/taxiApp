package com.kpavlov.modulepassengerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public record PassengerResponsePage (

        List<PassengerResponse> passengerResponses,

        int currentPage,

        int totalPages,

        long limit
) {}