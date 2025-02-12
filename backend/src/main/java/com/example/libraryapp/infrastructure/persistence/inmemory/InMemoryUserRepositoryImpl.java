package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.ports.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryUserRepositoryImpl implements UserRepository {
    private final ConcurrentHashMap<Long, User> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public List<User> findAllByLoansCountDesc(int limit) {
        return null;
    }

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
    public Optional<User> findByPersonId(Long personId) {
        return map.values().stream()
                .filter(user -> user.getPersonId().equals(personId))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public void incrementTotalBooksRequested(Long userId) {
        User user = map.get(userId);
        user.setTotalBooksRequested(user.getTotalBooksRequested() + 1);
        map.put(userId, user);
    }

    @Override
    public void decrementTotalBooksRequested(Long userId) {
        User user = map.get(userId);
        user.setTotalBooksRequested(user.getTotalBooksRequested() - 1);
        map.put(userId, user);
    }

    @Override
    public void incrementTotalBooksBorrowed(Long userId) {
        User user = map.get(userId);
        user.setTotalBooksBorrowed(user.getTotalBooksBorrowed() + 1);
        map.put(userId, user);
    }

    @Override
    public void decrementTotalBooksBorrowed(Long userId) {
        User user = map.get(userId);
        user.setTotalBooksBorrowed(user.getTotalBooksBorrowed() - 1);
        map.put(userId, user);
    }

    @Override
    public void reduceChargeByAmount(Long userId, BigDecimal amount) {
        User user = map.get(userId);
        if (user != null) {
            BigDecimal currentCharge = user.getCharge();
            user.setCharge(currentCharge.subtract(amount));
        }
    }

    @Override
    public long count() {
        return map.size();
    }

    @Override
    public long countNewRegisteredUsersByMonth(int month, int year) {
        return map.values().stream()
                .filter(user -> {
                    int monthVal = user.getRegistrationDate().getMonth().getValue();
                    int yearVal = user.getRegistrationDate().getYear();
                    return monthVal == month && yearVal == year;
                })
                .count();
    }

    public void putUser(User user) {
        map.put(user.getId(), user);
    }
}
