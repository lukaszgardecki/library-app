package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.model.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookFacade {
    private final GetBookUseCase getBookUseCase;
    private final AddBookUseCase addBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    public BookDto getBook(Long id) {
        Book book = getBookUseCase.execute(id);
        return BookMapper.toDto(book);
    }

    public BookDto addBook(BookToSaveDto bookToSave) {
        Book addedBook = addBookUseCase.execute(BookMapper.toModel(bookToSave));
        return BookMapper.toDto(addedBook);
    }

    public BookDto updateBook(Long id, BookToSaveDto bookToSave) {
        Book updatedBook = updateBookUseCase.execute(id, BookMapper.toModel(bookToSave));
        return BookMapper.toDto(updatedBook);
    }

    public void deleteBook(Long id) {
        deleteBookUseCase.execute(id);
    }
}
