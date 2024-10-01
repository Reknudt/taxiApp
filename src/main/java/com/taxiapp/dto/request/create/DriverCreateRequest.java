package com.taxiapp.dto.request.create;

import com.taxiapp.model.Gender;
import jakarta.validation.constraints.*;

public record DriverCreateRequest(

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
        Long carId
) {}