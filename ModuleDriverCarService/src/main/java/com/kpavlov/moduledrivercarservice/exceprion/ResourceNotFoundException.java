package com.kpavlov.moduledrivercarservice.exceprion;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}