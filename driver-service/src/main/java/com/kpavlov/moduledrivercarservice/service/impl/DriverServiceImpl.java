package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponsePage;
import com.kpavlov.moduledrivercarservice.exception.DuplicateFoundException;
import com.kpavlov.moduledrivercarservice.exception.DriverNotFoundException;
import com.kpavlov.moduledrivercarservice.mapper.DriverMapper;
import com.kpavlov.moduledrivercarservice.mapper.DriverPageMapper;
import com.kpavlov.moduledrivercarservice.model.Driver;
import com.kpavlov.moduledrivercarservice.model.DriverStatus;
import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
import com.kpavlov.moduledrivercarservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.kpavlov.moduledrivercarservice.util.ErrorMessages.ERROR_DUPLICATE_EMAIL;
import static com.kpavlov.moduledrivercarservice.util.ErrorMessages.ERROR_DUPLICATE_PHONE;
import static com.kpavlov.moduledrivercarservice.util.ErrorMessages.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverPageMapper driverPageMapper;
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
                .map(driverMapper::toResponse)
                .collect(Collectors.toList());

        return driverPageMapper.toDriverResponsePage(driverResponses, driverPage, limit);
    }

    private void checkCreateDriverData(DriverCreateRequest createDriverRequest){
        if (driverRepository.existsByEmail(createDriverRequest.email())) {
            String email = createDriverRequest.email();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_EMAIL,
                    new Object[]{email},
                    LocaleContextHolder.getLocale()));
        }
        if (driverRepository.existsByPhone(createDriverRequest.phone())) {
            String phone = createDriverRequest.phone();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_PHONE,
                    new Object[]{phone},
                    LocaleContextHolder.getLocale()));
        }
    }

    private void checkUpdateDriverData(DriverUpdateRequest updateDriverRequest, Driver existingDriver) {
        if (!updateDriverRequest.email().equals(existingDriver.getEmail()) && driverRepository.existsByEmail(updateDriverRequest.email())) {
            String email = updateDriverRequest.email();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_EMAIL,
                    new Object[]{email},
                    LocaleContextHolder.getLocale()));
        }
        if (!updateDriverRequest.phone().equals(existingDriver.getPhone()) && driverRepository.existsByPhone(updateDriverRequest.phone())) {
            String phone = updateDriverRequest.phone();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_PHONE,
                    new Object[]{phone},
                    LocaleContextHolder.getLocale()));
        }
    }

    private Driver findDriverByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(
                        () -> new DriverNotFoundException(messageSource.getMessage(
                                ERROR_NOT_FOUND,
                                new Object[]{id},
                                LocaleContextHolder.getLocale())));
    }
}