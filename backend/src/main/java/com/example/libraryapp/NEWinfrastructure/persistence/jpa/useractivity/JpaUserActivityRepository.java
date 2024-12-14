package com.example.libraryapp.NEWinfrastructure.persistence.jpa.useractivity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserActivityRepository extends JpaRepository<UserActivityEntity, Long> {

    @Query(value= """
                SELECT * FROM user_activity 
                WHERE (:userId IS NULL OR user_id = :userId) 
                AND (:type IS NULL OR type = :type)
            """,
            countQuery = """
                SELECT count(*) FROM action 
                WHERE (:userId IS NULL OR user_id = :userId) 
                AND (:type IS NULL OR type = :type)
        """,nativeQuery = true)
    Page<UserActivityEntity> findAllByParams(
            @Param("userId") Long userId,
            @Param("type") String type,
            Pageable pageable
    );
}
