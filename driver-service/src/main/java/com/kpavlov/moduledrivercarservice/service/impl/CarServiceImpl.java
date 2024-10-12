package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.dto.response.CarResponse;
import com.kpavlov.moduledrivercarservice.dto.response.CarResponsePage;
import com.kpavlov.moduledrivercarservice.exceprion.CarNotFoundException;
import com.kpavlov.moduledrivercarservice.exceprion.DuplicateFoundException;
import com.kpavlov.moduledrivercarservice.mapper.CarMapper;
import com.kpavlov.moduledrivercarservice.model.CarStatus;
import com.kpavlov.moduledrivercarservice.repository.CarRepository;
import com.kpavlov.moduledrivercarservice.service.CarService;
import com.kpavlov.moduledrivercarservice.dto.request.create.CarCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.CarUpdateRequest;
import com.kpavlov.moduledrivercarservice.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kpavlov.moduledrivercarservice.util.ErrorMessages.ERROR_DUPLICATE_REG_CODE;
import static com.kpavlov.moduledrivercarservice.util.ErrorMessages.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final MessageSource messageSource;

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
    public void softDeleteCar(Long id) {
        Car car = findCarByIdOrThrow(id);
        car.setStatus(CarStatus.DELETED);
        carMapper.toResponse(carRepository.save(car));
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarResponse getCarById(Long id) {
        Car car = findCarByIdOrThrow(id);
        return carMapper.toResponse(car);
    }

    @Override
    public CarResponsePage getAllCars(int offset, int limit) {
        Page<Car> carPage = carRepository.findAll(PageRequest.of(offset, limit));

        List<CarResponse> carResponses = carPage.getContent().stream()
                .map(car -> new CarResponse(car.getId(), car.getModel(), car.getRegistrationCode(), car.getStatus().name()))
                .collect(Collectors.toList());

        return new CarResponsePage(carResponses, carPage.getNumber(), carPage.getTotalPages(), carPage.getTotalElements());
    }

    private void checkCreateCarData(CarCreateRequest createCarRequest){
        Optional<Car> optionalCar = carRepository.getCarByRegistrationCode(createCarRequest.registrationCode());

        if (optionalCar.isPresent() && optionalCar.get().getStatus() != CarStatus.DELETED) {
            String regCode = createCarRequest.registrationCode();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_REG_CODE,
                    new Object[]{regCode},
                    null));
        }
    }

    private void checkUpdateCarData(CarUpdateRequest updateCarRequest, Car existingCar) {

        List<Car> cars = carRepository.findAllByRegistrationCodeEquals(updateCarRequest.registrationCode());

        for(Car c: cars) {
            if (c.getRegistrationCode().equals(existingCar.getRegistrationCode())
                    && !c.getStatus().equals(CarStatus.DELETED)
                    && !c.getId().equals(existingCar.getId())) {

                String regCode = existingCar.getRegistrationCode();

                throw new DuplicateFoundException(messageSource.getMessage(
                        ERROR_DUPLICATE_REG_CODE,
                        new Object[]{regCode},
                        null));
            }
        }
    }

    private Car findCarByIdOrThrow(Long id) {
        return carRepository.findById(id)
                .orElseThrow(
                        () -> { return new CarNotFoundException(messageSource.getMessage(
                                ERROR_NOT_FOUND,
                                new Object[]{id},
                                null));});
    }
}