package com.example.libraryapp.domain.bookitem.ports;

import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookItemRepositoryPort {
    Optional<BookItem> findById(Long id);

    Page<BookItem> findAll(Pageable pageable);

    Page<BookItem> findAllByBookId(Long bookId, Pageable pageable);

    Page<BookItem> findAllByRackId(Long rackId, Pageable pageable);

    BookItem save(BookItem bookItem);

    void deleteById(Long id);

    void updateStatus(Long id, BookItemStatus status);
}
