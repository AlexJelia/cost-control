package com.alex.repository.inmemory;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import com.alex.util.CostsUtil;
import com.alex.util.Util;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.alex.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static com.alex.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryCostRepository implements CostRepository {
    private Map<Integer, InMemoryBaseRepository<Cost>> usersCostsMap = new ConcurrentHashMap<>();

    {
        CostsUtil.COST_LIST.forEach(cost -> save(cost, USER_ID));

        save(new Cost(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Оплата домена", 510), ADMIN_ID);
        save(new Cost(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Штраф", 1500), ADMIN_ID);
    }

    //todo check
    @Override
    public Cost save(Cost cost, int userId) {
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


