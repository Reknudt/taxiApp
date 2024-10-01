package com.taxiapp.dto.response;

public record CarResponse(
        Long id,
        String model,
        String registrationCode
) {}