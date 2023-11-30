package com.example.libraryapp.domain.exception.bookItem;

public class BookIsAlreadyReturnedException extends RuntimeException{
    private static final String message = "The book is already returned";

    public BookIsAlreadyReturnedException() {
        super(message);
    }
}
