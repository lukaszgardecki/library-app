package com.example.libraryapp.NEWinfrastructure.persistence.inmemory;

import com.example.libraryapp.NEWdomain.user.model.User;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryUserRepositoryImpl implements UserRepository {
    private final ConcurrentHashMap<Long, User> map = new ConcurrentHashMap<>();
    private static long id = 0;

    public InMemoryUserRepositoryImpl() {}

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return map.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public User save(User user) {
        requireNonNull(user, "User cannot be null");
        if (user.getId() == null) {
            user.setId(++id);
        }
        map.put(user.getId(), requireNonNull(user));
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return map.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void incrementTotalBooksRequested(Long userId) {
        User user = map.get(userId);
        user.setTotalBooksRequested(user.getTotalBooksRequested() + 1);
        map.put(userId, user);
    }

    public void putUser(User user) {
        map.put(user.getId(), user);
    }
}
