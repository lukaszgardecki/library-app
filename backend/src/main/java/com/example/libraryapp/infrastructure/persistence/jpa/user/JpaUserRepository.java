package com.example.libraryapp.infrastructure.persistence.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPersonId(Long personId);

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
        SET u.totalBooksBorrowed = u.totalBooksBorrowed + 1
        WHERE u.id = :userId
    """)
    void decrementTotalBooksBorrowed(Long userId);

    @Query("""
        UPDATE UserEntity u
        SET u.charge = u.charge - :amount
        WHERE u.id = :userId
    """)
    void reduceChargeByAmount(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    @Query("""
        SELECT COUNT(u)
        FROM UserEntity u
        WHERE FUNCTION('MONTH', u.registrationDate) = :month
        AND FUNCTION('YEAR', u.registrationDate) = :year
    """)
    long countNewRegisteredUsersByMonth(@Param("month") int month, @Param("year") int year);

    // TODO: 12.02.2025 ta metoda jest źle liczy nie to co powinna
    @Query(value = """
        SELECT * 
        FROM users u 
        ORDER BY u.total_books_borrowed DESC 
        LIMIT :limit
    """, nativeQuery = true)
    List<UserEntity> findAllByLoansCountDesc(@Param("limit") int limit);

}
