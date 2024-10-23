package com.kpavlov.rideservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberValues {

    public static final String DEFAULT_OFFSET = "0";

    public static final String DEFAULT_LIMIT = "20";

    public static final int MIN_OFFSET = 0;

    public static final int MIN_LIMIT = 1;

    public static final int MAX_LIMIT = 100;
}
