package com.alex.repository.jpa;

import com.alex.model.Cost;
import com.alex.model.User;
import com.alex.repository.CostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static com.alex.util.TimeUtil.getEndExclusive;
import static com.alex.util.TimeUtil.getStartInclusive;

@Repository
@Transactional(readOnly = true)
public class JpaCostRepository implements CostRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Cost save(Cost cost, int userId) {
        if (!cost.isNew() && get(cost.getId(), userId) == null) {
            return null;
        }
        cost.setUser(em.getReference(User.class, userId));
        if (cost.isNew()) {
            em.persist(cost);
            return cost;
        } else {
            return em.merge(cost);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Cost.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Cost get(int id, int userId) {
        Cost cost = em.find(Cost.class, id);
        return cost != null && cost.getUser().getId() == userId ? cost : null;
    }

    @Override
    public List<Cost> getAll(int userId) {
        return em.createNamedQuery(Cost.ALL_SORTED, Cost.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<Cost> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return em.createNamedQuery(Cost.BETWEEN, Cost.class)
                .setParameter("userId", userId)
                .setParameter("startDate", getStartInclusive(startDate))
                .setParameter("endDate", getEndExclusive(endDate))
                .getResultList();
    }
}