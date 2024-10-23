package com.kpavlov.ratingservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String VALIDATION_FAILED_MESSAGE = "Validation failed";

    public static final String HTTP_MESSAGE_NOT_READABLE_MESSAGE = "Request body is missing or could not be read";

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected error occurred.";

    public static final String  ERROR_NOT_FOUND = "error.not.found";

    public static final String  ERROR_DRIVER_NOT_EXISTS = "error.duplicate.driver";

    public static final String  ERROR_PASSENGER_NOT_EXISTS = "error.duplicate.passenger";
}