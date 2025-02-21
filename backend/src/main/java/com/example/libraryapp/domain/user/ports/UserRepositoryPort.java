package com.example.libraryapp.domain.user.ports;

import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserListPreviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    List<User> findAllByLoansCountDesc(int limit);

    Page<User> findAll(Pageable pageable);

    Page<UserListPreviewProjection> findAllListPreviews(String query, Pageable pageable);

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
