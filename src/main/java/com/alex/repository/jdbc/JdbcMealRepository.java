package com.alex.repository.jdbc;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements CostRepository {

    @Override
    public Cost save(Cost cost, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public Cost get(int id, int userId) {
        return null;
    }

    @Override
    public List<Cost> getAll(int userId) {
        return null;
    }

    @Override
    public List<Cost> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}
