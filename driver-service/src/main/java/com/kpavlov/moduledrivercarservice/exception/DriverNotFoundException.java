package com.kpavlov.moduledrivercarservice.exception;

import lombok.Getter;

public class DriverNotFoundException extends RuntimeException {

    @Getter
    private final String messageKey;

    public DriverNotFoundException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }
}