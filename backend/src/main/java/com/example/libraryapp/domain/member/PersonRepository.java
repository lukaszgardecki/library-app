package com.example.libraryapp.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("""
        SELECT COUNT(p)
        FROM Person p
        WHERE YEAR(CURRENT DATE) - YEAR(p.dateOfBirth) BETWEEN :min AND :max
    """)
    long countByAgeBetween(int min, int max);
}
