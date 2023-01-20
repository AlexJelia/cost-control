package com.alex.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Cost.DELETE, query = "DELETE FROM Cost c WHERE c.id=:id and c.user.id=:userId "),
        @NamedQuery(name = Cost.BETWEEN, query = "SELECT c FROM Cost c WHERE c.user.id=:userId AND c.dateTime BETWEEN :startDate AND :endDate ORDER BY c.dateTime DESC"),
        @NamedQuery(name = Cost.ALL_SORTED, query = "SELECT c FROM Cost c WHERE c.user.id=:userId ORDER BY c.dateTime DESC "),
})
@Entity
@Table(name = "costs", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","date_time"}, name = "costs_unique_user_datetime_idx")})
public class Cost extends AbstractBaseEntity {
    public static final String DELETE = "Cost.delete";
    public static final String BETWEEN = "Cost.getBetween";
    public static final String ALL_SORTED = "Cost.getAll";

    @Column(name ="date_time",nullable = false)
    protected  LocalDateTime dateTime;

    @Column(name ="description",nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    protected  String description;

    @Column(name ="cost",nullable = false)
    @Range(min = 1, max = 1000000)
    protected  int cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public Cost(){
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
