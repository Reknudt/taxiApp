package com.kpavlov.ratingservice.mapper;

import com.kpavlov.ratingservice.dto.KafkaMessage;
import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingKafkaMapper {

    KafkaMessage toKafkaMessage(RatingCreateRequest ratingCreateRequest);

    KafkaMessage toKafkaMessage(RatingUpdateRequest ratingUpdateRequest);
}