package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.ports.out.BookRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class BookService {
    private final BookRepositoryPort bookRepository;

    List<Book> getAllById(List<BookId> ids) {
        return bookRepository.findAllById(ids);
    }

    Optional<Book> getBookById(BookId id) {
        return bookRepository.findById(id);
    }

    Book save(Book book) {
        return bookRepository.save(book);
    }
}
