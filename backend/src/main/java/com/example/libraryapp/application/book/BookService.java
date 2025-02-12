package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.ports.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class BookService {
    private final BookRepository bookRepository;

    Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    Book save(Book book) {
        return bookRepository.save(book);
    }
}
