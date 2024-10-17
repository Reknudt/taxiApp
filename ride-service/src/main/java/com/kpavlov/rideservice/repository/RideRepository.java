package com.kpavlov.rideservice.repository;

import com.kpavlov.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    Optional<Ride> findById(Long id);
}