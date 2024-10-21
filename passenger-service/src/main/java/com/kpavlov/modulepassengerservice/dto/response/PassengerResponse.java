package com.kpavlov.modulepassengerservice.dto.response;

public record PassengerResponse(

        long id,

        String name,

        String email,

        String phone,

        String status
) {}