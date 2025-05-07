package com.example.fineservice.infrastructure.persistence.jpa;

import com.example.fineservice.domain.model.values.FineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JpaFineRepository extends JpaRepository<FineEntity, Long> {

    List<FineEntity> findAllByUserId(Long userId);

    @Modifying
    @Query("""
        UPDATE FineEntity f
        SET f.status = :status
        WHERE f.id = :fineId
    """)
    void setStatus(@Param("fineId") Long fineId, @Param("status") FineStatus status);
}
