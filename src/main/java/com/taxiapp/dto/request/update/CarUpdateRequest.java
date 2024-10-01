package com.taxiapp.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CarUpdateRequest (

        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "^\\d{4}[A-Z]{2}-\\d$", message = "{field.phone}")
        String registrationCode
){}