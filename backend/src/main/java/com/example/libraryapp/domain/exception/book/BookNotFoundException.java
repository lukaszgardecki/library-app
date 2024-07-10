package com.example.libraryapp.domain.exception.book;

import com.example.libraryapp.management.Message;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super(Message.BOOK_NOT_FOUND.getMessage(id));
    }
}
