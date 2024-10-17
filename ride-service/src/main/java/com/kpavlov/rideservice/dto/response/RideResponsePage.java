package com.kpavlov.rideservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RideResponsePage {

    private List<RideResponse> rides;

    private int currentPage;

    private int totalPages;

    private int limit;
}