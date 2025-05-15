package com.example.loanservice.infrastructure.persistence.jpa;

import com.example.loanservice.domain.model.values.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

interface JpaBookItemLoanRepository extends JpaRepository<BookItemLoanEntity, Long> {

    List<BookItemLoanEntity> findAllByUserId(Long userId);

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE (:userId IS NULL OR b.userId = :userId)
        AND (:status IS NULL OR b.status = :status)
    """)
    Page<BookItemLoanEntity> findAllByParams(Long userId, @Param("status") LoanStatus status, Pageable pageable);

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.userId = :userId
        AND b.status = :status
    """)
    List<BookItemLoanEntity> findAllCurrentLoansByUserId(
            @Param("userId") Long userId,
            @Param("status") LoanStatus status
    );


    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.userId = :userId
        AND b.bookItemId = :bookItemId
        AND b.status = :status
    """)
    Optional<BookItemLoanEntity> findByParams(
            @Param("bookItemId") Long bookItemId,
            @Param("userId") Long userId,
            @Param("status") LoanStatus status
    );

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.bookItemId = :bookItemId
        AND b.status = :status
    """)
    Optional<BookItemLoanEntity> findByParams(
            @Param("bookItemId") Long bookItemId,
            @Param("status") LoanStatus status
    );

    // TODO: 04.02.2025 sprawdzić czy metoda poniżej robi porpawne zapytanie
    @Query(value = """
        SELECT b.subject, COUNT(l.id) AS total
        FROM book_loan l
        JOIN book_item bi ON l.book_item_id = bi.id
        JOIN book b ON bi.book_id = b.id
        GROUP BY b.subject
        ORDER BY total DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findTopSubjectsWithLoansCount(@Param("limit") int limit);

    @Query("""
        SELECT MONTH(l.creationDate) AS month, COUNT(l)
        FROM BookItemLoanEntity l
        WHERE l.creationDate >= :startDate AND l.creationDate <= :endDate
        GROUP BY MONTH(l.creationDate)
    """)
    List<Object[]> countBookItemLoansMonthly(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT
            FUNCTION('DAYOFWEEK', l.creationDate) AS day,
            COUNT(l) AS count
        FROM BookItemLoanEntity l
        WHERE l.creationDate >= :startDate AND l.creationDate <= :endDate AND l.status = :status
        GROUP BY FUNCTION('DAYOFWEEK', l.creationDate)
        ORDER BY FUNCTION('DAYOFWEEK', l.creationDate) ASC
    """)
    List<Object[]> countBookItemLoansByDay(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") LoanStatus status
    );

    @Query("""
        SELECT COUNT(b)
        FROM BookItemLoanEntity b
        WHERE b.creationDate >= ?#{#date.atStartOfDay()}
              AND b.creationDate < ?#{#date.plusDays(1).atStartOfDay()}
    """)
    long countAllByCreationDate(@Param("date") LocalDate date);

    @Query("""
       SELECT COUNT(DISTINCT l.userId)
       FROM BookItemLoanEntity l
       WHERE FUNCTION('MONTH', l.creationDate) = FUNCTION('MONTH', CURRENT_DATE)
       AND FUNCTION('YEAR', l.creationDate) = FUNCTION('YEAR', CURRENT_DATE)
    """)
    long countUniqueBorrowersInCurrentMonth();
}
