package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.exceprion.DuplicateFoundException;
import com.kpavlov.moduledrivercarservice.exceprion.DriverNotFoundException;
import com.kpavlov.moduledrivercarservice.mapper.DriverMapper;
import com.kpavlov.moduledrivercarservice.model.Driver;
import com.kpavlov.moduledrivercarservice.model.DriverStatus;
import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
import com.kpavlov.moduledrivercarservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final MessageSource messageSource;

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
    public void softDeleteDriver(Long id) {
        Driver driver = findDriverByIdOrThrow(id);
        driver.setStatus(DriverStatus.DELETED);
        driverMapper.toResponse(driverRepository.save(driver));
    }

    @Override
    @Transactional
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
            String errorMessage = String.format(
                    messageSource.getMessage(
                    "error.duplicate.email",
                    null,
                    null),
                    createDriverRequest.phone());
            throw new DuplicateFoundException(errorMessage);
        }
        if (driverRepository.existsByPhone(createDriverRequest.phone())) {
            String errorMessage = String.format(
                    messageSource.getMessage(
                    "error.duplicate.phone",
                     null,
                    null),
                    createDriverRequest.phone());
            throw new DuplicateFoundException(errorMessage);
        }
    }

    private void checkUpdateDriverData(DriverUpdateRequest updateDriverRequest, Driver existingDriver) {
        if (!updateDriverRequest.email().equals(existingDriver.getEmail()) && driverRepository.existsByEmail(updateDriverRequest.email())) {
            String errorMessage = String.format(messageSource.getMessage(
                    "error.duplicate.email",
                    null,
                    null),
                    updateDriverRequest.email());
            throw new DuplicateFoundException(errorMessage);
        }
        if (!updateDriverRequest.phone().equals(existingDriver.getPhone()) && driverRepository.existsByPhone(updateDriverRequest.phone())) {
            String errorMessage = String.format(
                    messageSource.getMessage(
                    "error.duplicate.phone",
                    null,
                    null),
                    updateDriverRequest.phone());
            throw new DuplicateFoundException(errorMessage);
        }
    }

    private Driver findDriverByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(
                        () -> { String errorMessage = String.format(
                                messageSource.getMessage(
                                "error.not.found",
                                null,
                                null),
                            id);
        return new DriverNotFoundException(errorMessage);});
    }
}