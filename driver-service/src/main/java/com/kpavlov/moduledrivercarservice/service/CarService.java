package com.kpavlov.moduledrivercarservice.service;

import com.kpavlov.moduledrivercarservice.dto.request.create.CarCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.CarUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.CarResponse;
import com.kpavlov.moduledrivercarservice.dto.response.CarResponsePage;

public interface CarService {

    CarResponse createCar(CarCreateRequest createCarRequest);

    CarResponse updateCar(Long carId, CarUpdateRequest updateCarRequest);

    void deleteCar(Long id);

    void softDeleteCar(Long id);

    CarResponse getCarById(Long id);

    CarResponsePage getAllCars(int offset, int limit);
}