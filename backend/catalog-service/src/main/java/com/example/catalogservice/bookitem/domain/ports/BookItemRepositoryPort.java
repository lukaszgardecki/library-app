package com.example.catalogservice.bookitem.domain.ports;

import com.example.catalogservice.book.domain.model.BookId;
import com.example.catalogservice.bookitem.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookItemRepositoryPort {
    Optional<BookItem> findById(BookItemId id);

    Page<BookItem> findAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, String query, Pageable pageable);

    BookItem save(BookItem bookItem);

    void deleteById(BookItemId id);

    Long countByParams(RackId rackId, ShelfId shelfId);

    void updateStatus(BookItemId id, BookItemStatus status);

    void updateBarcode(BookItemId id, BookItemBarcode barcode);
}
