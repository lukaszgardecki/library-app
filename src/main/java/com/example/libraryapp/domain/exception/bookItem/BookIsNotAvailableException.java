package com.example.libraryapp.domain.exception.bookItem;

public class BookIsNotAvailableException extends RuntimeException{
    private static final String message = "The book is not available";

    public BookIsNotAvailableException() {
        super(message);
    }
}
