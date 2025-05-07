package com.example.activityservice.infrastructure.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaActivityRepository extends JpaRepository<ActivityEntity, Long> {

    @Query(value = """
        SELECT u
        FROM ActivityEntity u
        WHERE (:userId IS NULL OR u.userId = :userId)
            AND (:type IS NULL OR u.type = :type)
    """,
    countQuery = """
        SELECT COUNT(u) FROM ActivityEntity u
        WHERE (:userId IS NULL OR u.userId = :userId)
            AND (:type IS NULL OR u.type = :type)
    """)
    Page<ActivityEntity> findAllByParams(
            @Param("userId") Long userId,
            @Param("type") String type,
            Pageable pageable
    );
}
