package com.example.libraryapp.domain.exception.libraryscanner;

public class InvalidCardNumberException extends RuntimeException{
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
