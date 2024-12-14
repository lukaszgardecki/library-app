package com.example.libraryapp.NEWdomain.user.ports;

import com.example.libraryapp.NEWdomain.user.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    User save(User user);

    Optional<User> findByEmail(String email);

    void incrementTotalBooksRequested(Long userId);

    void decrementTotalBooksRequested(Long userId);

    void incrementTotalBooksBorrowed(Long userId);

    void decrementTotalBooksBorrowed(Long userId);
}
