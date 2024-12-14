package com.example.libraryapp.NEWinfrastructure.persistence.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    @Query("""
            UPDATE UserEntity u
            SET u.totalBooksRequested = u.totalBooksRequested + 1
            WHERE u.id = :userId
    """)
    void incrementTotalBooksRequested(Long userId);

    @Query("""
            UPDATE UserEntity u
            SET u.totalBooksRequested = u.totalBooksRequested - 1
            WHERE u.id = :userId
    """)
    void decrementTotalBooksRequested(Long userId);

    @Query("""
            UPDATE UserEntity u
            SET u.totalBooksBorrowed = u.totalBooksBorrowed - 1
            WHERE u.id = :userId
    """)
    void incrementTotalBooksBorrowed(Long userId);

    @Query("""
            UPDATE UserEntity u
            SET u.totalBooksBorrowed = u.totalBooksBorrowed +ó 1
            WHERE u.id = :userId
    """)
    void decrementTotalBooksBorrowed(Long userId);
}
