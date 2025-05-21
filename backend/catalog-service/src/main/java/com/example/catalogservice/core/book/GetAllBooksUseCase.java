package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.BookId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetAllBooksUseCase {
    private final BookService bookService;

    List<Book> execute(List<BookId> ids) {
        return bookService.getAllById(ids);
    }
}
