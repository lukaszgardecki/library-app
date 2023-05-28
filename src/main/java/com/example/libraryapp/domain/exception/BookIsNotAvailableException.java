package com.example.libraryapp.domain.exception;

public class BookIsNotAvailableException extends RuntimeException{
    private static final String message = "The book is not available";

    public BookIsNotAvailableException() {
        super(message);
    }
}
