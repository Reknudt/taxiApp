package com.kpavlov.moduledrivercarservice.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CarUpdateRequest (

        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "^\\d{4}[A-Z]{2}-\\d$", message = "{field.code}")
        String registrationCode,

        @NotBlank(message = "{field.required}")
        String status
){}