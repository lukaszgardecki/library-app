package com.example.warehouseservice.infrastructure.persistence.jpa.rack;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface JpaRackRepository extends JpaRepository<RackEntity, Long> {

    @Query("""
        SELECT r
        FROM RackEntity r
        WHERE :query IS NULL OR :query = ''
            OR LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CAST(r.id AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CAST(r.shelvesCount AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<RackEntity> findAllByParams(String query, Pageable pageable);

    @Query("""
        SELECT r
        FROM RackEntity r
        WHERE :query IS NULL OR :query = ''
            OR LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CAST(r.id AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CAST(r.shelvesCount AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    List<RackEntity> findAllByParams(String query);
}
