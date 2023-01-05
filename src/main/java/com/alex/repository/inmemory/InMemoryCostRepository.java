package com.alex.repository.inmemory;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import com.alex.util.CostsUtil;
import com.alex.util.Util;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.alex.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static com.alex.repository.inmemory.InMemoryUserRepository.USER_ID;

public class InMemoryCostRepository implements CostRepository {
    private final Map<Integer, Map<Integer, Cost>> usersCostsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        CostsUtil.COST_LIST.forEach(meal -> save(meal, USER_ID));

        save(new Cost(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Оплата домена", 510), ADMIN_ID);
        save(new Cost(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Штраф", 1500), ADMIN_ID);
    }

    //todo check
    @Override
    public Cost save(Cost cost, int userId) {
        // We cannot use method reference "ConcurrentHashMap::new" here. It will be equivalent wrong "new ConcurrentHashMap<>(userId)"
        Map<Integer, Cost> costs = usersCostsMap.computeIfAbsent(userId, uId -> new ConcurrentHashMap<>());
        if (cost.isNew()) {
            cost.setId(counter.incrementAndGet());
            costs.put(cost.getId(), cost);
            return cost;
        }
        // treat case: update, but not present in storage
        return costs.computeIfPresent(cost.getId(), (id, oldCost) -> cost);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Cost> meals = usersCostsMap.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Cost get(int id, int userId) {
        Map<Integer, Cost> meals = usersCostsMap.get(userId);
        return meals == null ? null : meals.get(id);
    }

    public List<Cost> getAll(int userId) {
        Map<Integer, Cost> meals = usersCostsMap.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .sorted(Comparator.comparing(Cost::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Cost> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenInclusive(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Cost> getAllFiltered(int userId, Predicate<Cost> filter) {
        Map<Integer, Cost> meals = usersCostsMap.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Cost::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}


