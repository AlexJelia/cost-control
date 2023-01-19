package com.alex.service;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.alex.util.ValidationUtil.checkNotFoundWithId;

@Service
public class CostService {

    private final CostRepository repository;

    public CostService(CostRepository repository) {
        this.repository = repository;
    }

    public Cost get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Cost> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetween(startDate, endDate, userId);
    }

    public List<Cost> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Cost cost, int userId) {
        Assert.notNull(cost, "cost must not be null");
        checkNotFoundWithId(repository.save(cost, userId), cost.getId());
    }

    public Cost create(Cost cost, int userId) {
        Assert.notNull(cost, "cost must not be null");
        return repository.save(cost, userId);
    }

    public Cost getWithUser(int id, int userId) {
        return checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }

}