package com.kpavlov.moduledrivercarservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DriverResponsePage {

    private List<DriverResponse> drivers;

    private int currentPage;

    private int totalPages;

    private long totalElements;
}