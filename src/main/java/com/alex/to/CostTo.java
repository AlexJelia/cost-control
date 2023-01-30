package com.alex.to;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

//transfer object
public class CostTo {
    protected Integer id;
    protected final LocalDateTime dateTime;
    protected final String description;
    protected final int cost;
    protected final boolean excess;

    @ConstructorProperties({"id", "dateTime", "description", "cost", "excess"})
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostTo that = (CostTo) o;
        return cost == that.cost &&
                excess == that.excess &&
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, cost, excess);
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
