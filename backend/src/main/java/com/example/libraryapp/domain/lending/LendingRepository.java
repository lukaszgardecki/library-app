package com.example.libraryapp.domain.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
