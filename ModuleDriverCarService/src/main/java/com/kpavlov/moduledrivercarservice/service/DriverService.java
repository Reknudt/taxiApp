package com.kpavlov.moduledrivercarservice.service;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;

import java.util.List;

public interface DriverService {

    DriverResponse createDriver(DriverCreateRequest createDriverRequest);

    DriverResponse updateDriver(Long driverId, DriverUpdateRequest updateDriverRequest);

    void deleteDriver(Long id);

    DriverResponse softDeleteDriver(Long id);

    DriverResponse getDriverById(Long id);

    List<DriverResponse> getAllDrivers();
}