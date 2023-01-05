package com.alex.util;

import com.alex.model.Cost;
import com.alex.to.CostTo;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class CostsUtil {
    public static final int DEFAULT_COSTS_PER_DAY = 2000;
    public static final List<Cost> COST_LIST = Arrays.asList(
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Оплата Штрафа", 500),
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Расход на граничное значение", 100),
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Оплата мобильного", 500),
            new Cost(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static List<CostTo> getWithExcess(Collection<Cost> costs, int costsPerDay) {
        return getFilteredWithExcess(costs, LocalTime.MIN, LocalTime.MAX, costsPerDay);
    }

    public static List<CostTo> getFilteredWithExcess(Collection<Cost> costs, @Nullable LocalTime startTime,@Nullable LocalTime endTime, int costsPerDay) {
        Map<LocalDate, Integer> sumPerDay = costs.stream()
                .collect(Collectors.groupingBy(Cost::getDate, Collectors.summingInt(Cost::getCost)));
        return costs.stream()
                .filter(el -> Util.isBetweenInclusive(el.getTime(), startTime, endTime))
                .map(el -> new CostTo(el.getId(), el.getDateTime(), el.getDescription(), el.getCost(),
                        sumPerDay.get(el.getDate()) > costsPerDay))
                .collect(Collectors.toList());
    }
}