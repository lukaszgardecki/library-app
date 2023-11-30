package com.example.libraryapp.domain.exception.bookItem;

public class BookLimitException extends RuntimeException {
    public BookLimitException(String message) {
        super(message);
    }
}
