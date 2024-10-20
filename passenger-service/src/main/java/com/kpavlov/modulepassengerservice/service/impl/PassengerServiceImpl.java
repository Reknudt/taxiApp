package com.kpavlov.modulepassengerservice.service.impl;

import com.kpavlov.modulepassengerservice.dto.response.PassengerResponsePage;
import com.kpavlov.modulepassengerservice.exception.DuplicateFoundException;
import com.kpavlov.modulepassengerservice.exception.PassengerNotFoundException;
import com.kpavlov.modulepassengerservice.dto.request.create.PassengerCreateRequest;
import com.kpavlov.modulepassengerservice.dto.request.update.PassengerUpdateRequest;
import com.kpavlov.modulepassengerservice.dto.response.PassengerResponse;
import com.kpavlov.modulepassengerservice.mapper.PassengerMapper;
import com.kpavlov.modulepassengerservice.mapper.PassengerPageMapper;
import com.kpavlov.modulepassengerservice.model.Passenger;
import com.kpavlov.modulepassengerservice.model.PassengerStatus;
import com.kpavlov.modulepassengerservice.repository.PassengerRepository;
import com.kpavlov.modulepassengerservice.service.PassengerService;
import com.kpavlov.modulepassengerservice.validator.PassengerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.kpavlov.modulepassengerservice.util.ErrorMessages.ERROR_DUPLICATE_EMAIL;
import static com.kpavlov.modulepassengerservice.util.ErrorMessages.ERROR_DUPLICATE_PHONE;
import static com.kpavlov.modulepassengerservice.util.ErrorMessages.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerPageMapper passengerPageMapper;
    private final MessageSource messageSource;
    private final PassengerValidator passengerValidator;

    @Override
    @Transactional
    public PassengerResponse createPassenger(PassengerCreateRequest createPassengerRequest) {
        passengerValidator.checkCreatePassengerData(createPassengerRequest);

        Passenger passenger = passengerMapper.createRequestToEntity(createPassengerRequest);
        return passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    @Override
    @Transactional
    public PassengerResponse updatePassenger(Long id, PassengerUpdateRequest updatePassengerRequest) {
        Passenger passenger = findPassengerByIdOrThrow(id);

        passengerValidator.checkUpdatePassengerData(updatePassengerRequest, passenger);

        passengerMapper.updatePassengerFromUpdateRequest(updatePassengerRequest, passenger);
        return passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    @Override
    @Transactional
    public void softDeletePassenger(Long id) {
        Passenger passenger = findPassengerByIdOrThrow(id);
        passenger.setStatus(PassengerStatus.DELETED);
        passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    @Override
    @Transactional
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = findPassengerByIdOrThrow(id);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponsePage getAllPassengers(int offset, int limit) {
        Page<Passenger> passengerPage = passengerRepository.findAll(PageRequest.of(offset, limit));

        List<PassengerResponse> passengerResponses = passengerPage.getContent().stream()
                .map(passengerMapper::toResponse)
                .toList();

        return passengerPageMapper.toPassengerResponsePage(passengerResponses, passengerPage, limit);
    }

    private Passenger findPassengerByIdOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(
                        () -> new PassengerNotFoundException(messageSource.getMessage(
                                ERROR_NOT_FOUND,
                                new Object[]{id},
                                LocaleContextHolder.getLocale())));
    }
}