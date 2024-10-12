package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponsePage;
import com.kpavlov.moduledrivercarservice.exceprion.CarNotFoundException;
import com.kpavlov.moduledrivercarservice.exceprion.DuplicateFoundException;
import com.kpavlov.moduledrivercarservice.exceprion.DriverNotFoundException;
import com.kpavlov.moduledrivercarservice.mapper.DriverMapper;
import com.kpavlov.moduledrivercarservice.model.Driver;
import com.kpavlov.moduledrivercarservice.model.DriverStatus;
import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
import com.kpavlov.moduledrivercarservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.kpavlov.moduledrivercarservice.util.ErrorMessages.*;

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
    public DriverResponsePage getAllDrivers(int offset, int limit) {
        Page<Driver> driverPage = driverRepository.findAll(PageRequest.of(offset, limit));

        List<DriverResponse> driverResponses = driverPage.getContent().stream()
                .map(driver -> new DriverResponse(driver.getId(), driver.getPhone(), driver.getEmail(), driver.getName(), driver.getGender(), driver.getCarId(), driver.getStatus().name()))
                .collect(Collectors.toList());

        return new DriverResponsePage(driverResponses, driverPage.getNumber(), driverPage.getTotalPages(), driverPage.getTotalElements());
    }

    private void checkCreateDriverData(DriverCreateRequest createDriverRequest){
        if (driverRepository.existsByEmail(createDriverRequest.email())) {
            String email = createDriverRequest.email();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_EMAIL,
                    new Object[]{email},
                    null));
        }
        if (driverRepository.existsByPhone(createDriverRequest.phone())) {
            String phone = createDriverRequest.phone();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_PHONE,
                    new Object[]{phone},
                    null));
        }
    }

    private void checkUpdateDriverData(DriverUpdateRequest updateDriverRequest, Driver existingDriver) {
        if (!updateDriverRequest.email().equals(existingDriver.getEmail()) && driverRepository.existsByEmail(updateDriverRequest.email())) {
            String email = updateDriverRequest.email();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_EMAIL,
                    new Object[]{email},
                    null));
        }
        if (!updateDriverRequest.phone().equals(existingDriver.getPhone()) && driverRepository.existsByPhone(updateDriverRequest.phone())) {
            String phone = updateDriverRequest.phone();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_PHONE,
                    new Object[]{phone},
                    null));
        }
    }

    private Driver findDriverByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(
                        () -> { return new DriverNotFoundException(messageSource.getMessage(
                                ERROR_NOT_FOUND,
                                new Object[]{id},
                                null));});
    }
}