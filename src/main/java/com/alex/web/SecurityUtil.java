package com.alex.web;

import static com.alex.util.CostsUtil.DEFAULT_COSTS_PER_DAY;

public class SecurityUtil {

    private static int id = 1;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static int authUserCostsPerDay() {
        return DEFAULT_COSTS_PER_DAY;
    }
}