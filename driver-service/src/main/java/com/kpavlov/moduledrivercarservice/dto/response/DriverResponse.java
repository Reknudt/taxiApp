package com.kpavlov.moduledrivercarservice.dto.response;

import com.kpavlov.moduledrivercarservice.model.Gender;
import jakarta.validation.constraints.NotBlank;

public record DriverResponse(

        Long id,

        String phone,

        String name,

        String email,

        Gender gender,

        Long carId,

        String status
) {}