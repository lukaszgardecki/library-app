package com.example.libraryapp.domain.book.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Message.BOOK_NOT_FOUND.getMessage(id)");
    }
}
