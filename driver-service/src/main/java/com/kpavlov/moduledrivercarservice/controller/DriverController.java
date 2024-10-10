package com.kpavlov.moduledrivercarservice.controller;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.model.Driver;
import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
import com.kpavlov.moduledrivercarservice.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;
    private final DriverRepository driverRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sofDeleteDriver(@PathVariable Long id) {
        driverService.softDeleteDriver(id);
    }

    @GetMapping
    public Page<Driver> getAllDrivers(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return driverRepository.findAll(PageRequest.of(offset, limit));
    }
}