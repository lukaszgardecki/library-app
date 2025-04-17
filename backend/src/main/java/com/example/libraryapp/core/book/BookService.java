package com.example.libraryapp.core.book;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.ports.BookRepositoryPort;
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
