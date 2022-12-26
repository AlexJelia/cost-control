package com.alex.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Cost {
    private final LocalDateTime dateTime;

    private final String description;

    private final int cost;

    public Cost(LocalDateTime dateTime, String description, int cost) {
        this.dateTime = dateTime;
        this.description = description;
        this.cost = cost;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }
}
