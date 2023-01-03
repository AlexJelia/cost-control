package com.alex.repository.inmemory;

import com.alex.model.AbstractNamedEntity;
import com.alex.model.User;
import com.alex.repository.UserRepository;
import com.alex.util.UsersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.USER_LIST.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        repository.get(id);
        log.info("get {}", id);
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = new ArrayList<>(repository.values());
        Comparator<User> comparator = Comparator.comparing((User a) -> a.getName()).thenComparingInt(AbstractNamedEntity::getId);
        users.sort(comparator);
        System.out.println(users);
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        List<User> users = new ArrayList<>(repository.values());
        Optional<User> optional = users.stream()
                    .filter(user -> user.getEmail().contains(email))
                    .findFirst();
        return optional.orElse(null);
    }
}
