package com.kpavlov.moduledrivercarservice.service;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponsePage;

public interface DriverService {

    DriverResponse createDriver(DriverCreateRequest createDriverRequest);

    DriverResponse updateDriver(Long driverId, DriverUpdateRequest updateDriverRequest);

    void deleteDriver(Long id);

    void softDeleteDriver(Long id);

    DriverResponse getDriverById(Long id);

    DriverResponsePage getAllDrivers(int offset, int limit);
}