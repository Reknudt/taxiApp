package com.kpavlov.moduledrivercarservice.dto.response;

import com.kpavlov.moduledrivercarservice.model.Gender;

public record DriverResponse(

        Long id,

        String phone,

        String name,

        String email,

        Gender gender,

        Long carId,

        String status
) {}