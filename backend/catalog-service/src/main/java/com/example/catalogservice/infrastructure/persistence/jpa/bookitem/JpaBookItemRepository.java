package com.example.catalogservice.infrastructure.persistence.jpa.bookitem;

import com.example.catalogservice.domain.model.bookitem.values.BookItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface JpaBookItemRepository extends JpaRepository<BookItemEntity, Long> {

    @Query(
        value = """
            SELECT bi
            FROM BookItemEntity bi
            WHERE (:bookId IS NULL OR bi.bookId = :bookId)
                AND (:rackId IS NULL OR bi.rackId = :rackId)
                AND (:shelfId IS NULL OR bi.shelfId = :shelfId)
                AND (:query IS NULL OR LOWER(bi.barcode) LIKE LOWER(CONCAT('%', :query, '%')))
        """,
        countQuery = """
            SELECT COUNT(bi)
            FROM BookItemEntity bi
            WHERE (:bookId IS NULL OR bi.bookId = :bookId)
                AND (:rackId IS NULL OR bi.rackId = :rackId)
                AND (:shelfId IS NULL OR bi.shelfId = :shelfId)
                AND (:query IS NULL OR LOWER(bi.barcode) LIKE LOWER(CONCAT('%', :query, '%')))
    """)
    Page<BookItemEntity> findAllByParams(
            @Param("bookId") Long bookId,
            @Param("rackId") Long rackId,
            @Param("shelfId") Long shelfId,
            @Param("query") String query,
            Pageable pageable
    );

    @Modifying
    @Query("""
        UPDATE BookItemEntity bi
        SET bi.status = :status
        WHERE bi.id = :id
    """)
    void updateStatus(Long id, BookItemStatus status);

    @Modifying
    @Query("""
        UPDATE BookItemEntity bi
        SET bi.barcode = :barcode
        WHERE bi.id =:id
    """)
    void updateBarcode(Long id, String barcode);

    @Query("""
        SELECT COUNT(bi)
        FROM BookItemEntity bi
        WHERE (:rackId IS NULL OR bi.rackId = :rackId)
              AND (:shelfId IS NULL OR bi.shelfId = :shelfId)
    """)
    Long countByParams(@Param("rackId") Long rackId, @Param("shelfId") Long shelfId);
}
