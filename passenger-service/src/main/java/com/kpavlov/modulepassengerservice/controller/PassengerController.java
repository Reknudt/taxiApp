package com.kpavlov.modulepassengerservice.controller;

import com.kpavlov.modulepassengerservice.dto.request.create.PassengerCreateRequest;
import com.kpavlov.modulepassengerservice.dto.request.update.PassengerUpdateRequest;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponse;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponsePage;
import com.kpavlov.modulepassengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse createPassenger(@RequestBody @Valid PassengerCreateRequest createPassengerRequest) {
        return passengerService.createPassenger(createPassengerRequest);
    }

    @PutMapping("/{id}")
    public PassengerResponse updatePassenger(
            @PathVariable Long id,
            @RequestBody @Valid PassengerUpdateRequest updatePassengerRequest) {
        return passengerService.updatePassenger(id, updatePassengerRequest);
    }

    @GetMapping("/{id}")
    public PassengerResponse getById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    @DeleteMapping("/{id}/hard")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sofDeletePassenger(@PathVariable Long id) {
        passengerService.softDeletePassenger(id);
    }

    @GetMapping
    public PassengerResponsePage getAllPassengers(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                  @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return passengerService.getAllPassengers(offset, limit);
    }
}