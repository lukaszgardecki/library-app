package com.example.catalogservice.book.core;

import com.example.catalogservice.book.domain.model.Book;
import com.example.catalogservice.book.domain.model.BookId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetAllBooksUseCase {
    private final BookService bookService;

    List<Book> execute(List<BookId> ids) {
        return bookService.getAllById(ids);
    }
}
