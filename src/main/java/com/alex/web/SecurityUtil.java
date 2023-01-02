package com.alex.web;

import static com.alex.util.CostsUtil.DEFAULT_COSTS_PER_DAY;

public class SecurityUtil {

    public static int authUserId() {
        return 1;
    }

    public static int authUserCostsPerDay() {
        return DEFAULT_COSTS_PER_DAY;
    }
}