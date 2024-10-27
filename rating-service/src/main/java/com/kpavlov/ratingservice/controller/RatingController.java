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

import static com.kpavlov.ratingservice.util.PaginationDefaultValues.DEFAULT_LIMIT;
import static com.kpavlov.ratingservice.util.PaginationDefaultValues.DEFAULT_OFFSET;
import static com.kpavlov.ratingservice.util.PaginationDefaultValues.MAX_LIMIT;
import static com.kpavlov.ratingservice.util.PaginationDefaultValues.MIN_LIMIT;
import static com.kpavlov.ratingservice.util.PaginationDefaultValues.MIN_OFFSET;


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

    @PutMapping("/{id}/driverRate")
    public void updateDriverRate(
            @PathVariable long id,
            @RequestParam @Valid int rate) {
        ratingService.updateDriverRate(id, rate);
    }

    @PutMapping("/{id}/passengerRate")
    public void updatePassengerRate(
            @PathVariable long id,
            @RequestParam @Valid int rate) {
        ratingService.updatePassengerRate(id, rate);
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
    public RatingResponsePage getAllRatings(@RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                                @Min(MIN_OFFSET) Integer offset,
                                            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                                @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return ratingService.getAllRatings(offset, limit);
    }

    @GetMapping("/{id}/driver")
    public RatingResponsePage getRatingsByDriverId(@PathVariable long id,
                                            @RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                            @Min(MIN_OFFSET) Integer offset,
                                            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                            @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return ratingService.findRatingsByDriverId(id, offset, limit);
    }

    @GetMapping("/{id}/passenger")
    public RatingResponsePage getRatingsByPassengerId(@PathVariable long id,
                                            @RequestParam(value = "offset", defaultValue = DEFAULT_OFFSET)
                                            @Min(MIN_OFFSET) Integer offset,
                                            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT)
                                            @Min(MIN_LIMIT) @Max(MAX_LIMIT) Integer limit) {
        return ratingService.findRatingsByPassengerId(id, offset, limit);
    }
}