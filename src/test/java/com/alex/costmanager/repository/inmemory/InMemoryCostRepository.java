package com.alex.costmanager.repository.inmemory;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import com.alex.util.Util;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Repository
public class InMemoryCostRepository implements CostRepository {
    private Map<Integer, InMemoryBaseRepository<Cost>> usersCostsMap = new ConcurrentHashMap<>();

    @Override
    public Cost save(Cost cost, int userId) {
        Objects.requireNonNull(cost, "cost must not be null");
        InMemoryBaseRepository<Cost> costs = usersCostsMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return costs.save(cost);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Cost> costs = usersCostsMap.get(userId);
        return costs != null && costs.delete(id);
    }

    @Override
    public Cost get(int id, int userId) {
        InMemoryBaseRepository<Cost> costs = usersCostsMap.get(userId);
        return costs == null ? null : costs.get(id);
    }

    public List<Cost> getAll(int userId) {
        return getAllFiltered(userId,cost -> true);
    }

    @Override
    public List<Cost> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Objects.requireNonNull(startDateTime, "startDateTime must not be null");
        Objects.requireNonNull(endDateTime, "endDateTime must not be null");
        return getAllFiltered(userId, cost -> Util.isBetweenInclusive(cost.getDateTime(), startDateTime, endDateTime));
    }

    private List<Cost> getAllFiltered(int userId, Predicate<Cost> filter) {
        InMemoryBaseRepository<Cost> costs = usersCostsMap.get(userId);
        return costs == null ? Collections.emptyList() :
                costs.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Cost::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

