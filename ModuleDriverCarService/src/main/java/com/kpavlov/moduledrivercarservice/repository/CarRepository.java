package com.kpavlov.moduledrivercarservice.repository;

import com.kpavlov.moduledrivercarservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByRegistrationCode(String registrationCode);

    Car getCarByRegistrationCode(String registrationCode);

    List<Car> findAllByRegistrationCodeEquals(String registrationCode);
}