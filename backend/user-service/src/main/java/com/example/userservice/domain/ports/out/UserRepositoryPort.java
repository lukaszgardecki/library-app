package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.integration.fine.FineAmount;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.model.user.UserListPreviewProjection;
import com.example.userservice.domain.model.person.values.PersonId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    List<User> findAllByLoansCountDesc(int limit);

    Page<User> findAll(Pageable pageable);

    Page<UserListPreviewProjection> findAllListPreviews(String query, Pageable pageable);

    Optional<User> findById(UserId id);

    Optional<User> findByPersonId(PersonId personId);

    User save(User user);

    void deleteById(UserId id);

    void incrementTotalBooksRequested(UserId userId);

    void decrementTotalBooksRequested(UserId userId);

    void incrementTotalBooksBorrowed(UserId userId);

    void decrementTotalBooksBorrowed(UserId userId);

    void reduceChargeByAmount(UserId userId, FineAmount amount);

    long count();

    long countNewRegisteredUsersByMonth(int month, int year);
}
