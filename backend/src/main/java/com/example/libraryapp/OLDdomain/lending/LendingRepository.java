package com.example.libraryapp.OLDdomain.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LendingRepository extends JpaRepository<Lending, Long> {

    List<Lending> findAllByMemberId(Long memberId);

    @Query(value =
            """
            select * from Lending
            where book_item_id = :bookItemId
            and status = 'CURRENT'
            LIMIT 1
            """
            , nativeQuery = true)
    Optional<Lending> findCurrentLendingByBookItemId(@Param(value = "bookItemId") Long bookItemId);

    @Query("""
       SELECT COUNT(DISTINCT l.member.id)
       FROM Lending l
       WHERE FUNCTION('MONTH', l.creationDate) = FUNCTION('MONTH', CURRENT_DATE)
       AND FUNCTION('YEAR', l.creationDate) = FUNCTION('YEAR', CURRENT_DATE)
       """)
    long countActiveMembersThisMonth();

    @Query("""
       SELECT COUNT(l)
       FROM Lending l
       WHERE l.creationDate = CURRENT_DATE
       """)
    long countLendingsToday();

    @Query(value = """
       SELECT b.subject, COUNT(l.id) AS total
       FROM lending l
       JOIN book_item bi ON l.book_item_id = bi.id
       JOIN book b ON bi.book_id = b.id
       GROUP BY b.subject
       ORDER BY total DESC
       LIMIT :count
       """, nativeQuery = true)
    List<Object[]> findTopSubjectsWithLendingCount(@Param("count") int count);

    @Query("""
        SELECT MONTH(l.creationDate) AS month, COUNT(l)
        FROM Lending l
        WHERE l.creationDate >= :startDate AND l.creationDate <= :endDate
        GROUP BY MONTH(l.creationDate)
    """)
    List<Object[]> countLendingsByMonth(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT
            FUNCTION('DAYOFWEEK', l.creationDate) AS day,
            COUNT(l) AS count
        FROM Lending l
        WHERE l.creationDate >= :startDate AND l.creationDate <= :endDate AND l.status = :status
        GROUP BY FUNCTION('DAYOFWEEK', l.creationDate)
        ORDER BY FUNCTION('DAYOFWEEK', l.creationDate) ASC
    """)
    List<Object[]> countLendingsByDay(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") LendingStatus status
    );
}
