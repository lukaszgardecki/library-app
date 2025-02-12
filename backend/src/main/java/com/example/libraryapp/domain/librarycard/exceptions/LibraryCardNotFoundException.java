package com.example.libraryapp.domain.librarycard.exceptions;

public class LibraryCardNotFoundException extends RuntimeException{
    public LibraryCardNotFoundException(Long id) {
        super("Message.CARD_NOT_FOUND.getMessage(id)");
    }
}
