package com.alex.repository;

import com.alex.model.Cost;

import java.util.Collection;

public interface CostRepository {
    Cost save(Cost meal);

    boolean delete(int id);

    Cost get(int id);

    Collection<Cost> getAll();
}
