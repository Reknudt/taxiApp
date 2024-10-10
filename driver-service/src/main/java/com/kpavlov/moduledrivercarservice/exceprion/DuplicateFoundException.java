package com.kpavlov.moduledrivercarservice.exceprion;

public class DuplicateFoundException extends RuntimeException{

    public DuplicateFoundException(String message) {
        super(message);
    }
}