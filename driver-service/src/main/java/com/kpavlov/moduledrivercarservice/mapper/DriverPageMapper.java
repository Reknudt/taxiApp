package com.kpavlov.moduledrivercarservice.mapper;

import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponsePage;
import com.kpavlov.moduledrivercarservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverPageMapper {

    DriverResponsePage toDriverResponsePage(List<DriverResponse> driverResponses, Page<Driver> driverPage, int limit);
}