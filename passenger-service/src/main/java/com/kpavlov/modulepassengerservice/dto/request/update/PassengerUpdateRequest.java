package com.kpavlov.modulepassengerservice.dto.request.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PassengerUpdateRequest(

        @NotBlank(message = "{field.required}")
        String name,

        @NotBlank(message = "{field.required}")
        @Pattern(regexp = "\\+?[0-9]{7,15}", message = "{field.phone}")
        String phone,

        @NotBlank(message = "{field.required}")
        @Email(message = "{field.email}")
        String email,

        @NotBlank(message = "{field.required}")
        String status
) {}