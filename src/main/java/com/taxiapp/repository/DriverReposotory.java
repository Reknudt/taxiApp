package com.taxiapp.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DriverReposotory {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
