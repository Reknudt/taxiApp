package com.kpavlov.modulepassengerservice.service.impl;

import com.kpavlov.modulepassengerservice.repository.PassengerRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final PassengerRepository passengerRepository;

    @KafkaListener(topics = "passenger", groupId = "passenger_consumer")
    public void checkDriver(ConsumerRecord<String, String> record) {

        String id = record.value();

        boolean exist = passengerRepository.existsById(Long.parseLong(id));

        kafkaProducer.sendMessage("infoFromPassenger", String.valueOf(exist));
    }

    @KafkaListener(topics = "passengerFromRide", groupId = "passenger_consumer")
    public void checkDriverRide(ConsumerRecord<String, String> record) {

        String id = record.value();

        boolean exist = passengerRepository.existsById(Long.parseLong(id));

        kafkaProducer.sendMessage("infoFromPassengerToRide", String.valueOf(exist));
    }
}