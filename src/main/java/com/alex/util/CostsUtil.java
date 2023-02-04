package com.alex.util;

import com.alex.model.Cost;
import com.alex.to.CostTo;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CostsUtil {

    private CostsUtil() {
    }

    public static List<CostTo> getTransferObjects(Collection<Cost> costs, int costsPerDay) {
        return getFilteredTransferObjects(costs, LocalTime.MIN, LocalTime.MAX, costsPerDay);
    }

    public static List<CostTo> getFilteredTransferObjects(Collection<Cost> costs, @Nullable LocalTime startTime, @Nullable LocalTime endTime, int costsPerDay) {
        Map<LocalDate, Integer> sumPerDay = costs.stream()
                .collect(Collectors.groupingBy(Cost::getDate, Collectors.summingInt(Cost::getCost)));
        return costs.stream()
                .filter(el -> Util.isBetweenInclusive(el.getTime(), startTime, endTime))
                .map(el -> new CostTo(el.getId(), el.getDateTime(), el.getDescription(), el.getCost(),
                        sumPerDay.get(el.getDate()) > costsPerDay))
                .collect(Collectors.toList());
    }

    public static CostTo createTo(Cost cost, boolean excess) {
        return new CostTo(cost.getId(), cost.getDateTime(), cost.getDescription(), cost.getCost(), excess);
    }
}