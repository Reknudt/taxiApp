package com.kpavlov.modulepassengerservice.dto.response;

public record PassengerResponse(

        Long id,

        String name,

        String email,

        String phone,

        String status
) {}