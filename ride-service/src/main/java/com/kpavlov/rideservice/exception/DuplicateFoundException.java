package com.kpavlov.rideservice.exception;

import lombok.Getter;

public class DuplicateFoundException extends RuntimeException{

    @Getter
    private final String messageKey;

    public DuplicateFoundException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }
}