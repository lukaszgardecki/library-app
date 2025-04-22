package com.example.catalogservice.book.domain.ports;

import com.example.catalogservice.book.domain.model.Book;
import com.example.catalogservice.book.domain.model.BookId;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {

    List<Book> findAllById(List<BookId> ids);

    Optional<Book> findById(BookId id);

    Book save(Book book);

    void deleteById(BookId id);
}
