package com.kpavlov.ratingservice.controller;

import com.kpavlov.ratingservice.dto.request.create.RatingCreateRequest;
import com.kpavlov.ratingservice.dto.request.update.RatingUpdateRequest;
import com.kpavlov.ratingservice.dto.response.RatingResponse;
import com.kpavlov.ratingservice.dto.response.RatingResponsePage;
import com.kpavlov.ratingservice.service.RatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse createRating(@RequestBody @Valid RatingCreateRequest createRatingRequest) {
        return ratingService.createRating(createRatingRequest);
    }

    @PutMapping("/{id}")
    public RatingResponse updateRating(
            @PathVariable long id,
            @RequestBody @Valid RatingUpdateRequest updateRatingRequest) {
        return ratingService.updateRating(id, updateRatingRequest);
    }

    @PutMapping("/{id}/rate")
    public RatingResponse updateRate(
            @PathVariable long id,
            @RequestParam @Valid Integer rate) {
        return ratingService.updateRate(id, rate);
    }

    @GetMapping("/{id}")
    public RatingResponse getById(@PathVariable long id) {
        return ratingService.getRatingById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable long id) {ratingService.deleteRating(id);
    }

    @GetMapping
    public RatingResponsePage getAllRatings(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                  @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit) {
        return ratingService.getAllRatings(offset, limit);
    }
}