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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public boolean isExcess() {
        return excess;
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
