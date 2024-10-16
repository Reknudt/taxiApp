package com.kpavlov.moduledrivercarservice.exception;

public class DuplicateFoundException extends RuntimeException {

    public DuplicateFoundException(String message) {
        super(message);
    }
}