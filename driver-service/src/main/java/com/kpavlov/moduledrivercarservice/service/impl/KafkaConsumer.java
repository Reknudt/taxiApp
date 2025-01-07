package com.kpavlov.moduledrivercarservice.service.impl;

import com.kpavlov.moduledrivercarservice.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final KafkaProducer kafkaProducer;
    private final DriverRepository driverRepository;

    @KafkaListener(topics = "driver", groupId = "driver_consumer")
    public void checkDriver(ConsumerRecord<String, String> record) {

        String id = record.value();

        boolean exist = driverRepository.existsById(Long.parseLong(id));

        kafkaProducer.sendMessage("infoFromDriver", String.valueOf(exist));
    }

    @KafkaListener(topics = "driverFromRide", groupId = "driver_consumer")
    public void checkDriverRide(ConsumerRecord<String, String> record) {

        String id = record.value();

        boolean exist = driverRepository.existsById(Long.parseLong(id));

        kafkaProducer.sendMessage("infoFromDriverToRide", String.valueOf(exist));
    }
}