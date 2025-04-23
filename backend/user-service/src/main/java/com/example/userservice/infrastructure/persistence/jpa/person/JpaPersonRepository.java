package com.example.userservice.infrastructure.persistence.jpa.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JpaPersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query("""
        SELECT p
        FROM PersonEntity p
        WHERE :query IS NULL OR :query = ''
            OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<PersonEntity> findAllByQuery(@Param("query") String query, Pageable pageable);

    @Query("""
        SELECT p
        FROM PersonEntity p
        WHERE :query IS NULL OR :query = ''
            OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<PersonEntity> findAllByQuery(@Param("query") String query);

    @Query("""
        SELECT COUNT(p)
        FROM PersonEntity p
        WHERE YEAR(CURRENT DATE) - YEAR(p.dateOfBirth) BETWEEN :min AND :max
    """)
    long countByAgeBetween(int min, int max);

    @Query(value = """
        SELECT city, COUNT(*) AS userCount
        FROM person
        GROUP BY city
        ORDER BY userCount DESC
        LIMIT :limit
    """, nativeQuery = true)
    List<Object[]> findCitiesByUserCountDesc(@Param("limit") int limit);

}
