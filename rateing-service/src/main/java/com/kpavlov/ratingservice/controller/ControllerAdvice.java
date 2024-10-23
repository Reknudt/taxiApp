package com.kpavlov.ratingservice.controller;

import com.kpavlov.ratingservice.dto.response.error.ErrorResponse;
import com.kpavlov.ratingservice.dto.response.error.MultiErrorResponse;
import com.kpavlov.ratingservice.exception.DuplicateFoundException;
import com.kpavlov.ratingservice.exception.RatingNotFoundException;
import com.kpavlov.ratingservice.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RatingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerResourceNotFound(RatingNotFoundException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE)
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
                .message(ErrorMessages.VALIDATION_FAILED_MESSAGE)
                .errors(errors)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ErrorMessages.HTTP_MESSAGE_NOT_READABLE_MESSAGE)
                .build();
    }

    @ExceptionHandler(DuplicateFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handlerDuplicateFound(DuplicateFoundException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .message(e.getMessage())
                .build();
    }
}