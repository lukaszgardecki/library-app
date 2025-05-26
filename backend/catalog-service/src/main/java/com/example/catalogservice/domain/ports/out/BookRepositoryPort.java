package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.BookId;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {

    List<Book> findAllById(List<BookId> ids);

    Optional<Book> findById(BookId id);

    Book save(Book book);

    void deleteById(BookId id);
}
