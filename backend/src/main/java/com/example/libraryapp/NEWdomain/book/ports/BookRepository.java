package com.example.libraryapp.NEWdomain.book.ports;

import com.example.libraryapp.NEWdomain.book.model.Book;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);

    Book save(Book book);

    void deleteById(Long id);
}
