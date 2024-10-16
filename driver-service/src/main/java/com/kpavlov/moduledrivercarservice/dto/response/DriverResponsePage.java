package com.kpavlov.moduledrivercarservice.dto.response;

import java.util.List;

public record DriverResponsePage (

    List<DriverResponse> driverResponses,

    int currentPage,

    int totalPages,

    int limit
) {}