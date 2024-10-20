package com.kpavlov.moduledrivercarservice.repository;

import com.kpavlov.moduledrivercarservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}