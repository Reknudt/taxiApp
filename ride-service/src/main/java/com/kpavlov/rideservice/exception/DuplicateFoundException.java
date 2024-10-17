package com.kpavlov.rideservice.exception;

public class DuplicateFoundException extends RuntimeException{

    public DuplicateFoundException(String message) {
        super(message);
    }
}