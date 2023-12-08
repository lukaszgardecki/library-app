package com.example.libraryapp.domain.exception.bookItem;

import com.example.libraryapp.management.Message;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super(String.format(Message.BOOK_NOT_FOUND, id));
    }
}
