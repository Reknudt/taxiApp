package com.kpavlov.moduledrivercarservice.controller;

import com.kpavlov.moduledrivercarservice.dto.response.CarResponse;
import com.kpavlov.moduledrivercarservice.service.CarService;
import com.kpavlov.moduledrivercarservice.dto.request.create.CarCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.CarUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    private final CarService carService;

    @PostMapping
    public CarResponse createCar(@RequestBody @Valid CarCreateRequest createCarRequest) {
        return carService.createCar(createCarRequest);
    }

    @PutMapping("/{id}")
    public CarResponse updateUser(
            @PathVariable Long id,
            @RequestBody @Valid CarUpdateRequest updateCarRequest) {
        return carService.updateCar(id, updateCarRequest);
    }

    @GetMapping("/{id}")
    public CarResponse getById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}/hard")
    public void deleteUser(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @DeleteMapping("/{id}")
    public CarResponse softDeleteUser(@PathVariable Long id) {
        return carService.softDeleteCar(id);
    }

    @GetMapping
    public List<CarResponse> getAllUsers() {
        return carService.getAllCars();
    }
}