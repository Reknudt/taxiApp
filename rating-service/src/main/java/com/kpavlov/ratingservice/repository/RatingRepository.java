package com.kpavlov.ratingservice.repository;

import com.kpavlov.ratingservice.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findById(Long id);

    Page findAllByRideId(long status, Pageable pageable);

    Page findAllByPassengerId(long passengerId, Pageable pageable);

    Page findAllByDriverId(long driverId, Pageable pageable);
}