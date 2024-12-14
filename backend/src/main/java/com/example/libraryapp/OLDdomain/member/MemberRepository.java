package com.example.libraryapp.OLDdomain.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("""
            SELECT m FROM Member m WHERE
            (:query IS NULL OR :query = '' OR
            LOWER(m.person.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(m.person.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR
            LOWER(m.email) LIKE LOWER(CONCAT('%', :query, '%')) OR
            CAST(m.id AS string) LIKE CONCAT('%', :query, '%'))
            """)
    Page<Member> findAllByString(@Param("query") String query, Pageable pageable);

    @Query("""
       SELECT COUNT(m)
       FROM Member m
       WHERE FUNCTION('MONTH', m.dateOfMembership) = FUNCTION('MONTH', CURRENT_DATE)
       AND FUNCTION('YEAR', m.dateOfMembership) = FUNCTION('YEAR', CURRENT_DATE)
       """)
    int countMembersRegisteredThisMonth();

    @Query("""
         SELECT m
         FROM Member m
         ORDER BY m.totalBooksBorrowed DESC
         LIMIT 10
         """)
    List<Member> findTop10Borrowers();
}