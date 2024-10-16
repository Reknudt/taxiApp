package com.kpavlov.moduledrivercarservice.mapper;

import com.kpavlov.moduledrivercarservice.dto.request.create.CarCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.CarUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.CarResponse;
import com.kpavlov.moduledrivercarservice.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    Car createRequestToEntity(CarCreateRequest createCarRequest);

    void updateCarFromUpdateRequest(CarUpdateRequest updateCarRequest, @MappingTarget Car car);

    @Mapping(source = "car.id", target = "id")
    CarResponse toResponse(Car car);
}