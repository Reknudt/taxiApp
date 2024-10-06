package com.kpavlov.moduledrivercarservice.dto.response;

public record CarResponse(

        Long id,

        String model,

        String registrationCode,

        String status
) {}