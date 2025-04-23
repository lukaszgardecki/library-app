package com.example.userservice.infrastructure.persistence.jpa.user;

import com.example.userservice.domain.model.user.UserListPreviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    @Query(
            value = """
                    SELECT
                        u.id AS id,
                        u.registration_date AS registrationDate,
                        p.first_name AS firstName,
                        p.last_name AS lastName
                    FROM users u
                    JOIN person p ON u.person_id = p.id
                       OR LOWER(p.first_name) LIKE LOWER(CONCAT('%', :query, '%'))
                       OR LOWER(p.last_name) LIKE LOWER(CONCAT('%', :query, '%'))
                       OR LOWER(CAST(u.id AS CHAR)) LIKE LOWER(CONCAT('%', :query, '%'))
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM users u
                    JOIN person p ON u.person_id = p.id
                       OR LOWER(p.first_name) LIKE LOWER(CONCAT('%', :query, '%'))
                       OR LOWER(p.last_name) LIKE LOWER(CONCAT('%', :query, '%'))
                       OR LOWER(CAST(u.id AS CHAR)) LIKE LOWER(CONCAT('%', :query, '%'))
                    """,
            nativeQuery = true)
    Page<UserListPreviewProjection> findAllByQuery(@Param("query") String query, Pageable pageable);

    // TODO: 12.02.2025 ta metoda jest źle liczy nie to co powinna
    @Query(value = """
        SELECT * 
        FROM users
        ORDER BY total_books_borrowed DESC 
        LIMIT :limit
    """, nativeQuery = true)
    List<UserEntity> findAllByLoansCountDesc(@Param("limit") int limit);


    Optional<UserEntity> findByPersonId(Long personId);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.totalBooksRequested = u.totalBooksRequested + 1
        WHERE u.id = :userId
    """)
    void incrementTotalBooksRequested(Long userId);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.totalBooksRequested = u.totalBooksRequested - 1
        WHERE u.id = :userId
    """)
    void decrementTotalBooksRequested(Long userId);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.totalBooksBorrowed = u.totalBooksBorrowed - 1
        WHERE u.id = :userId
    """)
    void incrementTotalBooksBorrowed(Long userId);

    @Modifying
    @Query("""
        UPDATE UserEntity u
        SET u.totalBooksBorrowed = u.totalBooksBorrowed + 1
        WHERE u.id = :userId
    """)
    void decrementTotalBooksBorrowed(Long userId);

    @Modifying
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



}
