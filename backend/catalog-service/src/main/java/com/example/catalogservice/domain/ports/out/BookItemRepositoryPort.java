package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.*;
import com.example.catalogservice.domain.model.bookitem.values.*;
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
