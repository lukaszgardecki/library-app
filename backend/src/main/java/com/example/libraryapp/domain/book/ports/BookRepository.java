package com.example.libraryapp.domain.book.ports;

import com.example.libraryapp.domain.book.model.Book;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);

    Book save(Book book);

    void deleteById(Long id);
}
