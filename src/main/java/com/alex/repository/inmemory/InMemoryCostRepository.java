package com.alex.repository.inmemory;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import com.alex.util.CostsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryCostRepository implements CostRepository {
    private final Map<Integer, Cost> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        CostsUtil.COST_LIST.forEach(this::save);
    }

    @Override
    public Cost save(Cost cost) {
        if (cost.isNew()) {
            cost.setId(counter.incrementAndGet());
            repository.put(cost.getId(), cost);
            return cost;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(cost.getId(), (id, oldCost) -> cost);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Cost get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Cost> getAll() {
        return repository.values();
    }
}

