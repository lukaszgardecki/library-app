package com.example.libraryapp.NEWinfrastructure.persistence.jpa.bookitemloan;

import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaBookItemLoanRepository extends JpaRepository<BookItemLoanEntity, Long> {

    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE (:id IS NULL OR b.id = :id)
        AND (:status IS NULL OR b.status = :status)
""")
    Page<BookItemLoanEntity> findAllByParams(Long id, BookItemLoanStatus status, Pageable pageable);


    @Query("""
        SELECT b
        FROM BookItemLoanEntity b
        WHERE b.userId = :userId
        AND b.bookItemId = :bookItemId
        AND b.status = :status
        """)
    Optional<BookItemLoanEntity> findByParams(Long userId, Long bookItemId, BookItemLoanStatus status);
}
