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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.kpavlov.rideservice.util.PaginationDefaultValues.DEFAULT_LIMIT;
import static com.kpavlov.rideservice.util.PaginationDefaultValues.DEFAULT_OFFSET;
import static com.kpavlov.rideservice.util.PaginationDefaultValues.MAX_LIMIT;
import static com.kpavlov.rideservice.util.PaginationDefaultValues.MIN_LIMIT;
import static com.kpavlov.rideservice.util.PaginationDefaultValues.MIN_OFFSET;

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
                                          @RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                          @Min(MIN_OFFSET) Integer offset,
                                          @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                              @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return rideService.findRidesByDriverId(id, offset, limit);
    }

    @GetMapping("/{id}/passenger")
    public RideResponsePage getByPassengerId(@PathVariable long id,
                                             @RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                             @Min(MIN_OFFSET) Integer offset,
                                             @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                                 @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return rideService.findRidesByPassengerId(id, offset, limit);
    }

    @GetMapping("/status")
    public RideResponsePage getByStatus(@RequestParam RideStatus status,
                                        @RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                        @Min(MIN_OFFSET) Integer offset,
                                        @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                            @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return rideService.findRidesByStatus(status, offset, limit);
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
    public RideResponsePage getAllRides(@RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                            @Min(MIN_OFFSET) Integer offset,
                                        @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                            @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return rideService.getAllRides(offset, limit);
    }
}