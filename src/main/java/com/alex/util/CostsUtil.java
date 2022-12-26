package com.alex.util;

import com.alex.model.Cost;
import com.alex.model.CostTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.alex.util.TimeUtil.isBetweenInclusive;

public class CostsUtil {
    public static void main(String[] args) {
        List<Cost> costs = Arrays.asList(
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<CostTo> mealsTo = getFilteredWithExcess(costs, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<CostTo> getFilteredWithExcess(List<Cost> costs, LocalTime startTime, LocalTime endTime, int costsPerDay) {
        Map<LocalDate, Integer> sumPerDay = costs.stream()
                .collect(Collectors.groupingBy(Cost::getDate, Collectors.summingInt(Cost::getCost)));
        return costs.stream()
                .filter(el -> isBetweenInclusive(el.getTime(), startTime, endTime))
                .map(el -> new CostTo(el.getDateTime(), el.getDescription(), el.getCost(),
                        sumPerDay.get(el.getDate()) > costsPerDay))
                .collect(Collectors.toList());
    }
}