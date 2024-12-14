package com.example.libraryapp.OLDdomain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepositoryOLD extends JpaRepository<Person, Long> {

    @Query("""
        SELECT COUNT(p)
        FROM Person p
        WHERE YEAR(CURRENT DATE) - YEAR(p.dateOfBirth) BETWEEN :min AND :max
    """)
    long countByAgeBetween(int min, int max);

    @Query("""
        SELECT p.address.city, COUNT(p) AS userCount
        FROM Person p
        GROUP BY p.address.city
        ORDER BY userCount DESC
        LIMIT 10
    """)
    List<Object[]> findTop10CitiesWithUserCount();
}
