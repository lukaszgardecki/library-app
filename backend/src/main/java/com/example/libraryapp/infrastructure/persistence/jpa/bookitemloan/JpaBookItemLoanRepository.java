package com.example.libraryapp.infrastructure.persistence.jpa.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaBookItemLoanRepository extends JpaRepository<BookItemLoanEntity, Long> {

    List<BookItemLoanEntity> findAllByUserId(Long userId);

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE (:id IS NULL OR b.id = :id)
        AND (:status IS NULL OR b.status = :status)
    """)
    Page<BookItemLoanEntity> findAllByParams(Long id, @Param("status") BookItemLoanStatus status, Pageable pageable);

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.userId = :userId
        AND b.status = :status
    """)
    List<BookItemLoanEntity> findAllCurrentLoansByUserId(Long userId, BookItemLoanStatus status);


    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.userId = :userId
        AND b.bookItemId = :bookItemId
        AND b.status = :status
    """)
    Optional<BookItemLoanEntity> findByParams(Long bookItemId, Long userId, BookItemLoanStatus status);

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.bookItemId = :bookItemId
        AND b.status = :status
    """)
    Optional<BookItemLoanEntity> findByParams(Long bookItemId, BookItemLoanStatus status);

    // TODO: 04.02.2025 sprawdzić czy metoda poniżej robi porpawne zapytanie
    @Query(value = """
        SELECT b.subject, COUNT(l.id) AS total
        FROM book_item_loan_entity l
        JOIN book_item_entity bi ON l.book_item_id = bi.id
        JOIN book_entity b ON bi.book_id = b.id
        GROUP BY b.subject
        ORDER BY total DESC
        LIMIT :count
    """, nativeQuery = true)
    List<Object[]> findTopSubjectsWithLoansCount(@Param("count") int count);

    @Query("""
        SELECT MONTH(l.creationDate) AS month, COUNT(l)
        FROM BookItemLoanEntity l
        WHERE l.creationDate >= :startDate AND l.creationDate <= :endDate
        GROUP BY MONTH(l.creationDate)
    """)
    List<Object[]> countBookItemLoansMonthly(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
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
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") BookItemLoanStatus status
    );

    @Query("""
        SELECT COUNT(b)
        FROM BookItemLoanEntity b
        WHERE FUNCTION('DATE', b.creationDate) = :date
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
