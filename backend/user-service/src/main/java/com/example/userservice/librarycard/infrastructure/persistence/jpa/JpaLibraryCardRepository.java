package com.example.userservice.librarycard.infrastructure.persistence.jpa;

import com.example.userservice.librarycard.domain.model.LibraryCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JpaLibraryCardRepository extends JpaRepository<LibraryCardEntity, Long> {

    @Modifying
    @Query("""
        UPDATE LibraryCardEntity lc
        SET lc.status = :status
        WHERE lc.id = :cardId
    """)
    void changeStatusByUserId(@Param("status") LibraryCardStatus status, @Param("cardId") Long cardId);
}
