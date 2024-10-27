package com.kpavlov.ratingservice.exception;

import lombok.Getter;

public class RatingNotFoundException extends RuntimeException{

    @Getter
    private final String messageKey;

    public RatingNotFoundException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }
}