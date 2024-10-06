package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.dto.response.CarResponse;
import com.kpavlov.moduledrivercarservice.exceprion.DuplicateFoundException;
import com.kpavlov.moduledrivercarservice.exceprion.ResourceNotFoundException;
import com.kpavlov.moduledrivercarservice.mapper.CarMapper;
import com.kpavlov.moduledrivercarservice.model.CarStatus;
import com.kpavlov.moduledrivercarservice.repository.CarRepository;
import com.kpavlov.moduledrivercarservice.service.CarService;
import com.kpavlov.moduledrivercarservice.util.ErrorMessages;
import com.kpavlov.moduledrivercarservice.dto.request.create.CarCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.CarUpdateRequest;
import com.kpavlov.moduledrivercarservice.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    @Transactional
    public CarResponse createCar(CarCreateRequest createCarRequest) {
        checkCreateCarData(createCarRequest);

        Car car = carMapper.createRequestToEntity(createCarRequest);
        return carMapper.toResponse(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarResponse updateCar(Long id, CarUpdateRequest updateCarRequest) {
        Car car = findCarByIdOrThrow(id);

        checkUpdateCarData(updateCarRequest, car);

        carMapper.updateCarFromUpdateRequest(updateCarRequest, car);
        return carMapper.toResponse(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarResponse softDeleteCar(Long id) {
        Car car = findCarByIdOrThrow(id);
        car.setStatus(CarStatus.valueOf("DELETED"));
        return carMapper.toResponse(carRepository.save(car));
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarResponse getCarById(Long id) {
        Car car = findCarByIdOrThrow(id);
        return carMapper.toResponse(car);
    }

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::toResponse)
                .toList();
    }

    private void checkCreateCarData(CarCreateRequest createCarRequest){
        if (carRepository.existsByRegistrationCode(createCarRequest.registrationCode())) {
            Car car = carRepository.getCarByRegistrationCode(createCarRequest.registrationCode());

            if (car.getStatus() != CarStatus.DELETED) {
                throw new DuplicateFoundException(String.format(ErrorMessages.ALREADY_USED_MESSAGE, createCarRequest.registrationCode()));
            }
        }
    }

    private void checkUpdateCarData(CarUpdateRequest updateCarRequest, Car existingCar) {

        List<Car> cars = carRepository.findAllByRegistrationCodeEquals(updateCarRequest.registrationCode());

        for(Car c: cars) {
            if (c.getRegistrationCode().equals(existingCar.getRegistrationCode())
                    && !c.getStatus().equals(CarStatus.DELETED)
                    && !c.getId().equals(existingCar.getId())) {
                throw new DuplicateFoundException(String.format(ErrorMessages.ALREADY_USED_MESSAGE, updateCarRequest.registrationCode()));
            }

        }
    }

    private Car findCarByIdOrThrow(Long id) {
        return carRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_MESSAGE, "Car", id))
                );
    }
}