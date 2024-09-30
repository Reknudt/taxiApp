package com.taxiapp.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository {

    boolean existsByRegistrationCode(String registrationCode);
}
