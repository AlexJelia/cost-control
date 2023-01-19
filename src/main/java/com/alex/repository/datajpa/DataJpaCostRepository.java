package com.alex.repository.datajpa;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaCostRepository implements CostRepository {

    private final CrudCostRepository crudCostRepository;
    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaCostRepository(CrudCostRepository crudCostRepository, CrudUserRepository crudUserRepository) {
        this.crudCostRepository = crudCostRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public Cost save(Cost cost, int userId) {
        if (!cost.isNew() && get(cost.getId(), userId) == null) {
            return null;
        }
        cost.setUser(crudUserRepository.getOne(userId));
        return crudCostRepository.save(cost);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudCostRepository.delete(id, userId) != 0;
    }

    @Override
    public Cost get(int id, int userId) {
        return crudCostRepository.findById(id).filter(cost -> cost.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Cost> getAll(int userId) {
        return crudCostRepository.getAll(userId);
    }

    @Override
    public List<Cost> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudCostRepository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public Cost getWithUser(int id, int userId) {
        return crudCostRepository.getWithUser(id, userId);
    }
}
