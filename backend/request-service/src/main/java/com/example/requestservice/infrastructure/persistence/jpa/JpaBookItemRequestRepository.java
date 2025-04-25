package com.example.requestservice.infrastructure.persistence.jpa;

import com.example.requestservice.domain.model.BookItemRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JpaBookItemRequestRepository extends JpaRepository<BookItemRequestEntity, Long> {

    @Query("""
        SELECT br
        FROM BookItemRequestEntity br
        WHERE br.userId = :userId
            AND br.bookItemId = :bookItemId
    """)
    List<BookItemRequestEntity> findAll(Long bookItemId, Long userId);


    @Query("""
        SELECT br
        FROM BookItemRequestEntity br
        WHERE br.userId = :userId
            AND br.status IN :statusesToFind
    """)
    List<BookItemRequestEntity> findAllByUserIdAndStatuses(Long userId, List<BookItemRequestStatus> statusesToFind);

    @Query("""
        SELECT br
        FROM BookItemRequestEntity br
        WHERE br.bookItemId = :bookItemId
            AND br.status IN :statusesToFind
    """)
    List<BookItemRequestEntity> findAllByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statusesToFind);

    @Query("""
        SELECT br
        FROM BookItemRequestEntity br
        WHERE :status IS NULL OR br.status = :status
""")
    Page<BookItemRequestEntity> findAllByStatus(@Param("status") BookItemRequestStatus status, Pageable pageable);

    @Modifying
    @Query("""
        UPDATE BookItemRequestEntity br
        SET br.status = :status
        WHERE br.id = :id
    """)
    void setBookRequestStatus(Long id, BookItemRequestStatus status);
}
