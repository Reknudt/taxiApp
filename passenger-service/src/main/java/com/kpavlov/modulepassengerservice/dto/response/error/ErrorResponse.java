package com.kpavlov.modulepassengerservice.dto.response.error;

import lombok.Builder;

@Builder
public record ErrorResponse(

        int status,

        String message
) {}