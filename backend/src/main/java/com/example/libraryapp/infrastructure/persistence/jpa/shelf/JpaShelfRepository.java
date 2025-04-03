package com.example.libraryapp.infrastructure.persistence.jpa.shelf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaShelfRepository extends JpaRepository<ShelfEntity, Long> {

    @Query("""
        SELECT s
        FROM ShelfEntity s
        WHERE (:rackId IS NULL OR s.rackId = :rackId)
          AND (:query IS NULL OR :query = ''
            OR LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CAST(s.id AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CAST(s.rackId AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
          )
    """)
    Page<ShelfEntity> findAllByParams(Long rackId, String query, Pageable pageable);
}
