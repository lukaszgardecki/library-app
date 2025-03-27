package com.example.libraryapp.domain.book.ports;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;

import java.util.Optional;

public interface BookRepositoryPort {
    Optional<Book> findById(BookId id);

    Book save(Book book);

    void deleteById(BookId id);
}
