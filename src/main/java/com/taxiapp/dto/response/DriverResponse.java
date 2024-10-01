package com.taxiapp.dto.response;

import com.taxiapp.model.Car;
import com.taxiapp.model.Gender;
import java.time.LocalDate;

public record DriverResponse(
        Long id,
        String phone,
        String name,
        String email,
        Gender gender,
        Car car
) {}