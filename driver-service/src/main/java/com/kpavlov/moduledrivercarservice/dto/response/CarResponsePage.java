package com.kpavlov.moduledrivercarservice.dto.response;

import java.util.List;

public record CarResponsePage (
        List<CarResponse> carResponses,

        int currentPage,

        int totalPages,

        int limit
) {}