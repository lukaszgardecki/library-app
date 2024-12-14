package com.example.libraryapp.NEWapplication.book;

import com.example.libraryapp.NEWdomain.book.model.Book;
import com.example.libraryapp.NEWdomain.book.ports.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class BookService {
    private final BookRepository bookRepository;

    Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    Book save(Book book) {
        return bookRepository.save(book);
    }
}
