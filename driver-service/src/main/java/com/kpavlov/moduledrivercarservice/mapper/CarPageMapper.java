package com.kpavlov.moduledrivercarservice.mapper;

import com.kpavlov.moduledrivercarservice.dto.response.CarResponse;
import com.kpavlov.moduledrivercarservice.dto.response.CarResponsePage;
import com.kpavlov.moduledrivercarservice.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarPageMapper {

    CarResponsePage toCarResponsePage(List<CarResponse> carResponses, Page<Car> carPage, int limit);
}