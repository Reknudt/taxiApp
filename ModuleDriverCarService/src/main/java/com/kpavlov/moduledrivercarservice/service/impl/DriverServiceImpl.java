package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.exceprion.DuplicateFoundException;
import com.kpavlov.moduledrivercarservice.exceprion.ResourceNotFoundException;
import com.kpavlov.moduledrivercarservice.mapper.DriverMapper;
import com.kpavlov.moduledrivercarservice.model.Driver;
import com.kpavlov.moduledrivercarservice.model.DriverStatus;
import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
import com.kpavlov.moduledrivercarservice.service.DriverService;
import com.kpavlov.moduledrivercarservice.util.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional
    public DriverResponse createDriver(DriverCreateRequest createDriverRequest) {
        checkCreateDriverData(createDriverRequest);

        Driver driver = driverMapper.createRequestToEntity(createDriverRequest);
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    @Override
    @Transactional
    public DriverResponse updateDriver(Long id, DriverUpdateRequest updateDriverRequest) {
        Driver driver = findDriverByIdOrThrow(id);

        checkUpdateDriverData(updateDriverRequest, driver);

        driverMapper.updateDriverFromUpdateRequest(updateDriverRequest, driver);
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    @Override
    @Transactional
    public DriverResponse softDeleteDriver(Long id) {
        Driver driver = findDriverByIdOrThrow(id);
        driver.setStatus(DriverStatus.valueOf("DELETED"));
        return driverMapper.toResponse(driverRepository.save(driver));
    }

    @Override
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    @Override
    public DriverResponse getDriverById(Long id) {
        Driver driver = findDriverByIdOrThrow(id);
        return driverMapper.toResponse(driver);
    }

    @Override
    public List<DriverResponse> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(driverMapper::toResponse)
                .toList();
    }

    private void checkCreateDriverData(DriverCreateRequest createDriverRequest){
        if (driverRepository.existsByEmail(createDriverRequest.email())) {
            throw new DuplicateFoundException(String.format(ErrorMessages.ALREADY_USED_MESSAGE, createDriverRequest.email()));
        }
        if (driverRepository.existsByPhone(createDriverRequest.phone())) {
            throw new DuplicateFoundException(String.format(ErrorMessages.ALREADY_USED_MESSAGE, createDriverRequest.phone()));
        }
    }

    private void checkUpdateDriverData(DriverUpdateRequest updateDriverRequest, Driver existingDriver) {
        if (!updateDriverRequest.email().equals(existingDriver.getEmail()) && driverRepository.existsByEmail(updateDriverRequest.email())) {
            throw new DuplicateFoundException(String.format(ErrorMessages.ALREADY_USED_MESSAGE, updateDriverRequest.email()));
        }
        if (!updateDriverRequest.phone().equals(existingDriver.getPhone()) && driverRepository.existsByPhone(updateDriverRequest.phone())) {
            throw new DuplicateFoundException(String.format(ErrorMessages.ALREADY_USED_MESSAGE, updateDriverRequest.phone()));
        }
    }

    private Driver findDriverByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_MESSAGE, "Driver", id))
                );
    }
}