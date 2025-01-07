package com.kpavlov.rideservice.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException{

    @Getter
    private final String messageKey;

    public ResourceNotFoundException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }
}