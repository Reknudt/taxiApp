package com.kpavlov.moduledrivercarservice.controller;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/driver")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public DriverResponse createDriver(@RequestBody @Valid DriverCreateRequest createDriverRequest) {
        return driverService.createDriver(createDriverRequest);
    }

    @PutMapping("/{id}")
    public DriverResponse updateDriver(
            @PathVariable Long id,
            @RequestBody @Valid DriverUpdateRequest updateDriverRequest) {
        return driverService.updateDriver(id, updateDriverRequest);
    }

    @GetMapping("/{id}")
    public DriverResponse getById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @DeleteMapping("/{id}/hard")
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }

    @DeleteMapping("/{id}")
    public DriverResponse sofDeleteDriver(@PathVariable Long id) {
        return driverService.softDeleteDriver(id);
    }

    @GetMapping
    public List<DriverResponse> getAllDrivers() {
        return driverService.getAllDrivers();
    }
}