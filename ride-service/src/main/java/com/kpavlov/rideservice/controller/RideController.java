package com.kpavlov.rideservice.controller;

import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;
import com.kpavlov.rideservice.model.RideStatus;
import com.kpavlov.rideservice.service.RideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rides")
public class RideController {

    private final RideService rideService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RideResponse createRide(@RequestBody @Valid RideCreateRequest createRideRequest) {
        return rideService.createRide(createRideRequest);
    }

    @PutMapping("/{id}")
    public RideResponse updateRide(
            @PathVariable long id,
            @RequestBody @Valid RideUpdateRequest updateRideRequest) {
        return rideService.updateRide(id, updateRideRequest);
    }

    @PatchMapping("/{id}")
    public void updateStatus(
            @PathVariable long id,
            @RequestParam RideStatus status) {
        rideService.updateStatus(id, status);
    }

    @GetMapping("/{id}")
    public RideResponse getById(@PathVariable long id) {
        return rideService.getRideById(id);
    }

    @GetMapping("/{id}/driver")
    public RideResponsePage getByDriverId(@PathVariable long id,
                                          @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                          @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit){
        return rideService.getRideByDriverId(id, offset, limit);
    }

    @GetMapping("/{id}/passenger")
    public RideResponsePage getByPassengerId(@PathVariable long id,
                                @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return rideService.getRideByPassengerId(id, offset, limit);
    }

    @GetMapping("/status")
    public RideResponsePage getByStatus(@RequestParam RideStatus status,
                                    @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                    @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit){
        return rideService.getRideByStatus(status, offset, limit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteRide(@PathVariable long id) {
        rideService.softDeleteRide(id);
    }

    @DeleteMapping("/{id}/hard")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@PathVariable long id) {
        rideService.deleteRide(id);
    }

    @GetMapping
    public RideResponsePage getAllRides(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                        @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return rideService.getAllRides(offset, limit);
    }
}