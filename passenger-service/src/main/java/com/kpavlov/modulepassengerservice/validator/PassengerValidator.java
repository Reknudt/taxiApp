package com.kpavlov.modulepassengerservice.validator;

import com.kpavlov.modulepassengerservice.dto.request.create.PassengerCreateRequest;
import com.kpavlov.modulepassengerservice.dto.request.update.PassengerUpdateRequest;
import com.kpavlov.modulepassengerservice.exception.DuplicateFoundException;
import com.kpavlov.modulepassengerservice.model.Passenger;
import com.kpavlov.modulepassengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import static com.kpavlov.modulepassengerservice.util.ErrorMessages.ERROR_DUPLICATE_EMAIL;
import static com.kpavlov.modulepassengerservice.util.ErrorMessages.ERROR_DUPLICATE_PHONE;

@Component
@RequiredArgsConstructor
public class PassengerValidator {

    private final PassengerRepository passengerRepository;
    private final MessageSource messageSource;

    public void checkCreatePassengerData(PassengerCreateRequest createPassengerRequest) {
        if (passengerRepository.existsByEmail(createPassengerRequest.email())) {
            String email = createPassengerRequest.email();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_EMAIL,
                    new Object[]{email},
                    LocaleContextHolder.getLocale()));
        }
        if (passengerRepository.existsByPhone(createPassengerRequest.phone())) {
            String phone = createPassengerRequest.phone();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_PHONE,
                    new Object[]{phone},
                    LocaleContextHolder.getLocale()));
        }
    }

    public void checkUpdatePassengerData(PassengerUpdateRequest updatePassengerRequest,
                                          Passenger existingPassenger) {

        if (!updatePassengerRequest.email().equals(existingPassenger.getEmail()) &&
                passengerRepository.existsByEmail(updatePassengerRequest.email())) {

            String email = updatePassengerRequest.email();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_EMAIL,
                    new Object[]{email},
                    LocaleContextHolder.getLocale()));
        }
        if (!updatePassengerRequest.phone().equals(existingPassenger.getPhone()) &&
                passengerRepository.existsByPhone(updatePassengerRequest.phone())) {

            String phone = updatePassengerRequest.phone();

            throw new DuplicateFoundException(messageSource.getMessage(
                    ERROR_DUPLICATE_PHONE,
                    new Object[]{phone},
                    LocaleContextHolder.getLocale()));
        }
    }
}