package com.example.libraryapp.domain.book.ports;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {

    List<Book> findAllById(List<BookId> ids);

    Optional<Book> findById(BookId id);

    Book save(Book book);

    void deleteById(BookId id);
}
