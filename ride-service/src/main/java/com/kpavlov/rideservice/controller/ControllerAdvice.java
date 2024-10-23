package com.kpavlov.rideservice.controller;

import com.kpavlov.rideservice.dto.response.error.ErrorResponse;
import com.kpavlov.rideservice.dto.response.error.MultiErrorResponse;
import com.kpavlov.rideservice.exception.DuplicateFoundException;
import com.kpavlov.rideservice.exception.RideNotFoundException;
import com.kpavlov.rideservice.util.HttpErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static com.kpavlov.rideservice.util.HttpErrorMessages.ERROR_NOT_FOUND;
import static com.kpavlov.rideservice.util.HttpErrorMessages.ERROR_NO_WAY;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(RideNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerResourceNotFound(RideNotFoundException e) {
        String localizedMessage = messageSource.getMessage(
                e.getMessage(),
                new Object[]{e.getMessageKey()},
                LocaleContextHolder.getLocale());

        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(localizedMessage)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultiErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return MultiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpErrorMessages.VALIDATION_FAILED_MESSAGE)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpErrorMessages.HTTP_MESSAGE_NOT_READABLE_MESSAGE)
                .build();
    }

    @ExceptionHandler(DuplicateFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicateFound(DuplicateFoundException e) {
        String localizedMessage = messageSource.getMessage(
                e.getMessage(),
                null,
                LocaleContextHolder.getLocale());

        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(localizedMessage)
                .build();
    }
}