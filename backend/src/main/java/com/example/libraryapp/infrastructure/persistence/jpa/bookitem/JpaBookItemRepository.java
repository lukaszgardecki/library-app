package com.example.libraryapp.infrastructure.persistence.jpa.bookitem;

import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JpaBookItemRepository extends JpaRepository<BookItemEntity, Long> {

    @Query(value = """
        SELECT bi
        FROM BookItemEntity bi
        WHERE (:bookId IS NULL OR bi.bookId = :bookId)
    """,
    countQuery = """
        SELECT COUNT(bi)
        FROM BookItemEntity bi
        WHERE (:bookId IS NULL OR bi.bookId = :bookId)
    """)
    Page<BookItemEntity> findAllByParams(@Param("bookId") Long bookId, Pageable pageable);

    @Query("""
        UPDATE BookItemEntity bi
        SET bi.status = :status
        WHERE bi.id = :id
    """)
    void updateStatus(Long id, BookItemStatus status);
}
