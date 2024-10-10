package com.kpavlov.moduledrivercarservice.mapper;

import com.kpavlov.moduledrivercarservice.dto.request.create.DriverCreateRequest;
import com.kpavlov.moduledrivercarservice.dto.request.update.DriverUpdateRequest;
import com.kpavlov.moduledrivercarservice.dto.response.DriverResponse;
import com.kpavlov.moduledrivercarservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverMapper {

    @Mapping(target = "id", ignore = true)
    Driver createRequestToEntity(DriverCreateRequest createDriverRequest);

    void updateDriverFromUpdateRequest(DriverUpdateRequest updateDriverRequest, @MappingTarget Driver driver);

    @Mapping(source = "driver.id", target = "id")
    DriverResponse toResponse(Driver driver);
}