package com.kpavlov.moduledrivercarservice.dto.response.error;

import lombok.Builder;
import java.util.Map;

@Builder
public record MultiErrorResponse(

        int status,

        String message,

        Map<String, String> errors
) {}