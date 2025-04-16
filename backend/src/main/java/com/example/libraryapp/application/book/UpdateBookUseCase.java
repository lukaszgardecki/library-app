package com.example.libraryapp.application.book;

import com.example.libraryapp.domain.book.exceptions.BookNotFoundException;
import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.model.BookId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateBookUseCase {
    private final BookService bookService;

    Book execute(BookId id, Book fieldsToUpdate) {
        Book book = bookService.getBookById(id)
                .map(b -> updateBookFields(b, fieldsToUpdate))
                .orElseThrow(() -> new BookNotFoundException(id));
        return bookService.save(book);
    }

    private Book updateBookFields(Book bookToUpdate, Book fieldsToUpdate) {
        if (fieldsToUpdate.getTitle() != null) bookToUpdate.setTitle(fieldsToUpdate.getTitle());
        if (fieldsToUpdate.getSubject() != null) bookToUpdate.setSubject(fieldsToUpdate.getSubject());
        if (fieldsToUpdate.getPublisher() != null) bookToUpdate.setPublisher(fieldsToUpdate.getPublisher());
        if (fieldsToUpdate.getISBN() != null) bookToUpdate.setISBN(fieldsToUpdate.getISBN());
        if (fieldsToUpdate.getLanguage() != null) bookToUpdate.setLanguage(fieldsToUpdate.getLanguage());
        if (fieldsToUpdate.getPages() != null) bookToUpdate.setPages(fieldsToUpdate.getPages());
        return bookToUpdate;
    }
}
