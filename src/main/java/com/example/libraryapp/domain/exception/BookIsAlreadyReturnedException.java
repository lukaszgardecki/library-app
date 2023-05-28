package com.example.libraryapp.domain.exception;

public class BookIsAlreadyReturnedException extends RuntimeException{
    private static final String message = "The book is already returned";

    public BookIsAlreadyReturnedException() {
        super(message);
    }
}
