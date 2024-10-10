package com.kpavlov.moduledrivercarservice.exceprion;

public class DriverNotFoundException extends RuntimeException{

    public DriverNotFoundException(String message) {
        super(message);
    }
}