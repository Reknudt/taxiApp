package com.kpavlov.ratingservice.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    public boolean driverExist = false;
    public boolean rideExist = false;
    public boolean passengerExist = false;

    @KafkaListener(topics = "infoFromDriver", groupId = "rating_consumer")
    public void checkDriver(String message) {

        driverExist = Boolean.parseBoolean(message);
    }

    @KafkaListener(topics = "infoFromRide", groupId = "rating_consumer")
    public void checkRide(String message) {

        rideExist = Boolean.parseBoolean(message);
    }

    @KafkaListener(topics = "infoFromPassenger", groupId = "rating_consumer")
    public void checkPassenger(String message) {

        passengerExist = Boolean.parseBoolean(message);
    }
}