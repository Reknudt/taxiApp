package com.kpavlov.modulepassengerservice.exception;

public class DuplicateFoundException extends RuntimeException {

    public DuplicateFoundException(String message) {
        super(message);
    }
}