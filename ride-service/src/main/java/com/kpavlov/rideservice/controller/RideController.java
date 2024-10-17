package com.kpavlov.rideservice.controller;

import com.kpavlov.rideservice.dto.request.create.RideCreateRequest;
import com.kpavlov.rideservice.dto.request.update.RideUpdateRequest;
import com.kpavlov.rideservice.dto.response.RideResponse;
import com.kpavlov.rideservice.dto.response.RideResponsePage;
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
            @PathVariable Long id,
            @RequestBody @Valid RideUpdateRequest updateRideRequest) {
        return rideService.updateRide(id, updateRideRequest);
    }

    @PutMapping("/{id}/completed")
    public void updateStatusCompleted(
            @PathVariable Long id) {
        rideService.updateStatusCompleted(id);
    }

    @PutMapping("/{id}/accepted")
    public void updateStatusActivated(
            @PathVariable Long id) {
        rideService.updateStatusAccepted(id);
    }

    @PutMapping("/{id}/to_passenger")
    public void updateStatusOnTheWayToPassenger(
            @PathVariable Long id) {
        rideService.updateStatusOnTheWayToPassenger(id);
    }

    @PutMapping("/{id}/to_destination")
    public void updateStatusOnTheWayToDestination(
            @PathVariable Long id) {
        rideService.updateStatusOnTheWayToDestination(id);
    }

    @PutMapping("/{id}/cancelled")
    public void updateStatusCancelled(@PathVariable Long id) {
        rideService.updateStatusCancelled(id);
    }

    @GetMapping("/{id}")
    public RideResponse getById(@PathVariable Long id) {
        return rideService.getRideById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
    }

    @GetMapping
    public RideResponsePage getAllRides(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                  @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return rideService.getAllRides(offset, limit);
    }
}