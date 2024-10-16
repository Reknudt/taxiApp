package com.kpavlov.moduledrivercarservice.dto.request.create;

import com.kpavlov.moduledrivercarservice.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DriverCreateRequest (

        @NotBlank(message = "{field.required}")
        String name,

        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "\\+?[0-9]{7,15}", message = "{field.phone}")
        String phone,

        @NotBlank(message = "{field.required}")
        @Email(message = "{field.email}")
        String email,

        @NotNull(message = "{field.required}")
        Gender gender,

        @NotNull(message = "{field.null}")
        Long carId,

        @NotBlank(message = "{field.required}")
        String status
) {}