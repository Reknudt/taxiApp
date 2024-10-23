package com.kpavlov.ratingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RatingResponsePage {

    private List<RatingResponse> rates;

    private int currentPage;

    private int totalPages;

    private long totalElements;
}