package com.taxiapp.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CarCreateRequest (

        @NotBlank(message = "{field.required}")
        String model,

        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "^\\d{4}[A-Z]{2}-\\d$", message = "{field.phone}")
        String registrationCode
){}