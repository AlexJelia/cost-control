package com.alex.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Cost extends AbstractBaseEntity {
    protected LocalDateTime dateTime;
    protected String description;
    protected int cost;

    public Cost() {
    }

    //todo check
    public Cost(Cost c) {
        this(c.getId(),c.getDateTime(),c.getDescription(),c.getCost());
    }

    public Cost(LocalDateTime dateTime, String description, int cost) {
        this(null, dateTime, description, cost);
    }

    public Cost(Integer id, LocalDateTime dateTime, String description, int cost) {
        super(id);
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
