package com.example.libraryapp.core.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookFacade {
    private final GetAllBooksUseCase getAllBooksUseCase;
    private final GetBookUseCase getBookUseCase;
    private final AddBookUseCase addBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    public List<BookDto> getBooksByIds(List<BookId> ids) {
        return getAllBooksUseCase.execute(ids).stream()
                .map(BookMapper::toDto)
                .toList();
    }

    public BookDto getBook(BookId id) {
        Book book = getBookUseCase.execute(id);
        return BookMapper.toDto(book);
    }

    public BookDto addBook(BookToSaveDto bookToSave) {
        Book addedBook = addBookUseCase.execute(BookMapper.toModel(bookToSave));
        return BookMapper.toDto(addedBook);
    }

    public BookDto updateBook(BookId id, BookToSaveDto bookToSave) {
        Book updatedBook = updateBookUseCase.execute(id, BookMapper.toModel(bookToSave));
        return BookMapper.toDto(updatedBook);
    }

    public void deleteBook(BookId id) {
        deleteBookUseCase.execute(id);
    }
}
