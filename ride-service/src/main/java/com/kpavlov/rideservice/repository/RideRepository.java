package com.kpavlov.rideservice.repository;

import com.kpavlov.rideservice.model.Ride;
import com.kpavlov.rideservice.model.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    Page findAllByStatus(RideStatus status, Pageable pageable);

    Page findAllByPassengerId(long passengerId, Pageable pageable);

    Page findAllByDriverId(long driverId, Pageable pageable);
}