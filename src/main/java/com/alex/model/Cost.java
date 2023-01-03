package com.alex.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Cost extends AbstractBaseEntity {
    protected Integer id;
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int cost;

    public Cost(LocalDateTime dateTime, String description, int cost) {
        this(null, dateTime, description, cost);
    }

    public Cost(Integer id, LocalDateTime dateTime, String description, int cost) {
        super(id);
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.cost = cost;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public boolean isNew() {
        return id == null;
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
