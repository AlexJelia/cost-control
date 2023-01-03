package com.alex.to;

import java.time.LocalDateTime;

//transfer object
public class CostTo {
    protected Integer id;
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int cost;
    protected final boolean excess;

    public CostTo(Integer id, LocalDateTime dateTime, String description, int cost, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.cost = cost;
        this.excess = excess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Integer getId() {
        return id;
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
        return "CostTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", excess=" + excess +
                '}';
    }
}
