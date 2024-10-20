package com.kpavlov.rideservice.repository;

import com.kpavlov.rideservice.model.Ride;
import com.kpavlov.rideservice.model.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    Optional<Ride> findById(Long id);

    Page findAllByStatus(RideStatus status, Pageable pageable);

    Page findAllByPassengerId(Long passengerId, Pageable pageable);

    Page findAllByDriverId(Long driverId, Pageable pageable);
}