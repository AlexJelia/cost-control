package com.alex.costmanager.repository.inmemory;

import com.alex.model.AbstractBaseEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alex.model.AbstractBaseEntity.START_SEQ;

@Repository
public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

     static final  AtomicInteger counter = new AtomicInteger(START_SEQ);

     Map<Integer, T> map = new ConcurrentHashMap<>();

    public T save(T entry) {
        if (entry.isNew()) {
            entry.setId(counter.incrementAndGet());
            map.put(entry.getId(), entry);
            return entry;
        }
        return map.computeIfPresent(entry.getId(), (id, oldT) -> entry);
    }

    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    public T get(int id) {
        return map.get(id);
    }

    Collection<T> getCollection() {
        return map.values();
    }

    void put(T entity) {
        map.put(entity.getId(), entity);
    }
}