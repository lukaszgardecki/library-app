package com.example.libraryapp.domain.bookItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookItemRepository extends JpaRepository<BookItem, Long> {
    Optional<BookItem> findByBarcode(String bookBarcode);
}
