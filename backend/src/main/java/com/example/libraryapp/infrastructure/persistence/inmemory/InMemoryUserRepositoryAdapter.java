package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.user.model.*;
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryUserRepositoryAdapter implements UserRepositoryPort {
    private final ConcurrentHashMap<UserId, User> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public List<User> findAllByLoansCountDesc(int limit) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        List<User> users = map.values().stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
        return new PageImpl<>(users, pageable, map.size());
    }

    @Override
    public Page<UserListPreviewProjection> findAllListPreviews(String query, Pageable pageable) {
        // TODO: 18.02.2025 ?? 
        return null;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean existsByEmail(Email email) {
        return map.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public User save(User user) {
        requireNonNull(user, "User cannot be null");
        if (user.getId() == null) {
            user.setId(new UserId(++id));
        }
        map.put(user.getId(), requireNonNull(user));
        return user;
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return map.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findByPersonId(PersonId personId) {
        return map.values().stream()
                .filter(user -> user.getPersonId().equals(personId))
                .findFirst();
    }

    @Override
    public void deleteById(UserId id) {
        map.remove(id);
    }

    @Override
    public void incrementTotalBooksRequested(UserId userId) {
        User user = map.get(userId);
        user.setTotalBooksRequested(new TotalBooksRequested(user.getTotalBooksRequested().value() + 1));
        map.put(userId, user);
    }

    @Override
    public void decrementTotalBooksRequested(UserId userId) {
        User user = map.get(userId);
        user.setTotalBooksRequested(new TotalBooksRequested(user.getTotalBooksRequested().value() - 1));
        map.put(userId, user);
    }

    @Override
    public void incrementTotalBooksBorrowed(UserId userId) {
        User user = map.get(userId);
        user.setTotalBooksBorrowed(new TotalBooksBorrowed(user.getTotalBooksBorrowed().value() + 1));
        map.put(userId, user);
    }

    @Override
    public void decrementTotalBooksBorrowed(UserId userId) {
        User user = map.get(userId);
        user.setTotalBooksBorrowed(new TotalBooksBorrowed(user.getTotalBooksBorrowed().value() - 1));
        map.put(userId, user);
    }

    @Override
    public void reduceChargeByAmount(UserId userId, FineAmount amount) {
        User user = map.get(userId);
        if (user != null) {
            BigDecimal currentCharge = user.getCharge().value();
            user.setCharge(new UserCharge(currentCharge.subtract(amount.value())));
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
                    int monthVal = user.getRegistrationDate().value().getMonth().getValue();
                    int yearVal = user.getRegistrationDate().value().getYear();
                    return monthVal == month && yearVal == year;
                })
                .count();
    }

    public void putUser(User user) {
        map.put(user.getId(), user);
    }
}
