package com.example.libraryapp.domain.bookitem.ports;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemBarcode;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookItemRepositoryPort {
    Optional<BookItem> findById(BookItemId id);

    Page<BookItem> findAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, Pageable pageable);

    BookItem save(BookItem bookItem);

    void deleteById(BookItemId id);

    void updateStatus(BookItemId id, BookItemStatus status);

    void updateBarcode(BookItemId id, BookItemBarcode barcode);
}
