package com.example.libraryapp.NEWinfrastructure.persistence.jpa.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface JpaBookItemRequestRepository extends JpaRepository<BookItemRequestEntity, Long> {

    @Query("""
        SELECT br FROM BookItemRequestEntity br
        WHERE br.userId = :userId
            AND br.bookItemId = :bookItemId
        """)
    Optional<BookItemRequestEntity> find(Long bookItemId, Long userId);


    Page<BookItemRequestEntity> findAllByStatus(BookItemRequestStatus status, Pageable pageable);

    @Query("""
        UPDATE BookItemRequestEntity br
        SET br.status = :status
        WHERE br.id = :id
        """)
    void setBookRequestStatus(Long id, BookItemRequestStatus status);

    @Query("""
        SELECT br FROM BookItemRequestEntity br
        WHERE br.bookItemId = :bookItemId
            AND br.status IN :statusesToFind
        """)
    List<BookItemRequestEntity> findByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statusesToFind);
}
