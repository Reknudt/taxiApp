package com.kpavlov.rideservice.service.impl;

import com.kpavlov.rideservice.repository.RideRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final RideRepository rideRepository;
    public boolean driverExist = false;
    public boolean passengerExist = false;

    @KafkaListener(topics = "ride", groupId = "ride_consumer")
    public void checkDriver(ConsumerRecord<String, String> record) {

        String id = record.value();

        boolean exist = rideRepository.existsById(Long.parseLong(id));

        kafkaProducer.sendMessage("infoFromRide", String.valueOf(exist));
    }

    @KafkaListener(topics = "infoFromDriverToRide", groupId = "rating_consumer")
    public void checkDriver(String message) {

        driverExist = Boolean.parseBoolean(message);
    }

    @KafkaListener(topics = "infoFromPassengerToRide", groupId = "rating_consumer")
    public void checkPassenger(String message) {

        passengerExist = Boolean.parseBoolean(message);
    }
}