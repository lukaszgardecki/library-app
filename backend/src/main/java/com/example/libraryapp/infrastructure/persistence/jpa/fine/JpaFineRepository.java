package com.example.libraryapp.infrastructure.persistence.jpa.fine;

import com.example.libraryapp.domain.fine.model.FineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JpaFineRepository extends JpaRepository<FineEntity, Long> {

    List<FineEntity> findAllByUserId(Long userId);

    @Query("""
        UPDATE FineEntity f
        SET f.status = :status
        WHERE f.id = :fineId
    """)
    void setStatus(@Param("fineId") Long fineId, @Param("status") FineStatus status);
}
