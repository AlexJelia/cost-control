package com.alex.repository.datajpa;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaCostRepository implements CostRepository{

    @Autowired
    private CrudCostRepository crudRepository;

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
    public List<Cost> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return null;
    }
}
