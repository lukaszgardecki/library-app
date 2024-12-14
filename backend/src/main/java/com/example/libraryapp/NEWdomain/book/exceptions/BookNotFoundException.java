package com.example.libraryapp.NEWdomain.book.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Message.BOOK_NOT_FOUND.getMessage(id)");
    }
}
