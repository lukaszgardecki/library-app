package com.example.libraryapp.domain.user.ports;

import com.example.libraryapp.domain.user.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAllByLoansCountDesc(int limit);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByPersonId(Long personId);

    User save(User user);

    void deleteById(Long id);

    void incrementTotalBooksRequested(Long userId);

    void decrementTotalBooksRequested(Long userId);

    void incrementTotalBooksBorrowed(Long userId);

    void decrementTotalBooksBorrowed(Long userId);

    void reduceChargeByAmount(Long userId, BigDecimal amount);

    long count();

    long countNewRegisteredUsersByMonth(int month, int year);
}
