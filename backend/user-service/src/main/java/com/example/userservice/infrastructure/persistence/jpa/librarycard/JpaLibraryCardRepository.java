package com.example.userservice.infrastructure.persistence.jpa.librarycard;

import com.example.userservice.domain.model.librarycard.values.LibraryCardStatus;
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
