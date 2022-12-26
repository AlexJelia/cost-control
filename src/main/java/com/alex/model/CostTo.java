package com.alex.model;

import java.time.LocalDateTime;

public class CostTo {
    private final LocalDateTime dateTime;

    private final String description;

    private final int cost;

    private final boolean excess;

    public CostTo(LocalDateTime dateTime, String description, int cost, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.cost = cost;
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", costs=" + cost +
                ", excess=" + excess +
                '}';
    }
}
