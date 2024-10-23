package com.kpavlov.rideservice.exception;

import lombok.Getter;

public class RideNotFoundException extends RuntimeException{

    @Getter
    private final String messageKey;

    public RideNotFoundException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }
}