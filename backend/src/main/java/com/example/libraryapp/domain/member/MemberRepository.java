package com.example.libraryapp.domain.member;

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
    List<Member> findAllByString(@Param("query") String query);
}