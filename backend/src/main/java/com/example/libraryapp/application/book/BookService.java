package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class BookService {
    private final BookRepositoryPort bookRepository;

    Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    Book save(Book book) {
        return bookRepository.save(book);
    }
}
