package com.kpavlov.moduledrivercarservice.exceprion;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message) {
        super(message);
    }
}