package com.example.libraryapp.domain.user.ports;

import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.userservice.common.person.model.PersonId;
import com.example.userservice.common.user.model.Email;
import com.example.userservice.common.user.model.User;
import com.example.userservice.common.user.model.UserId;
import com.example.userservice.common.user.model.UserListPreviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    List<User> findAllByLoansCountDesc(int limit);

    Page<User> findAll(Pageable pageable);

    Page<UserListPreviewProjection> findAllListPreviews(String query, Pageable pageable);

    Optional<User> findById(UserId id);

    boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email email);

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
