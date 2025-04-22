package com.example.libraryapp.core.book;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetAllBooksUseCase {
    private final BookService bookService;

    List<Book> execute(List<BookId> ids) {
        return bookService.getAllById(ids);
    }
}
