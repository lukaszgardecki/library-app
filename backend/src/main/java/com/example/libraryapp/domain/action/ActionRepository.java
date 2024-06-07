package com.example.libraryapp.domain.action;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @Query(value = "SELECT * FROM action " +
            "WHERE (:memberId IS NULL OR member_id = :memberId) " +
            "AND (:type IS NULL OR type = :type)",
            countQuery = "SELECT count(*) FROM action " +
                    "WHERE (:memberId IS NULL OR member_id = :memberId) " +
                    "AND (:type IS NULL OR type = :type)",
            nativeQuery = true)
    Page<Action> findAllByParams(@Param("memberId") Long memberId,
                                 @Param("type") String type,
                                 Pageable pageable);
}
