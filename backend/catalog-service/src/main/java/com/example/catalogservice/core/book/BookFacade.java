package com.example.catalogservice.core.book;

import com.example.catalogservice.domain.model.book.Book;
import com.example.catalogservice.domain.model.book.values.BookId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookFacade {
    private final GetAllBooksUseCase getAllBooksUseCase;
    private final GetBookUseCase getBookUseCase;
    private final AddBookUseCase addBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    public List<Book> getBooksByIds(List<BookId> ids) {
        return getAllBooksUseCase.execute(ids);
    }

    public Book getBook(BookId id) {
        return getBookUseCase.execute(id);
    }

    public Book addBook(Book bookToSave) {
        return addBookUseCase.execute(bookToSave);
    }

    public Book updateBook(BookId id, Book bookToSave) {
        return updateBookUseCase.execute(id, bookToSave);
    }

    public void deleteBook(BookId id) {
        deleteBookUseCase.execute(id);
    }
}
