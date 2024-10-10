package com.kpavlov.moduledrivercarservice.repository;

import com.kpavlov.moduledrivercarservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> getCarByRegistrationCode(String registrationCode);

    List<Car> findAllByRegistrationCodeEquals(String registrationCode);
}