package com.example.libraryapp.domain.bookItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookItemRepository extends JpaRepository<BookItem, Long> {
    Optional<BookItem> findByBarcode(String bookBarcode);

    Optional<BookItem> findByIdAndBookId(Long id, Long bookId);

    List<BookItem> findByBookId(Long bookId);

    Page<BookItem> findByBookId(Long bookId, Pageable pageable);

}
