package com.example.libraryapp.domain.bookitemrequest.exceptions;

public class BookItemRequestNotFoundException extends RuntimeException {

    public BookItemRequestNotFoundException() {
        super("Message.RESERVATION_NOT_FOUND.getMessage()");
    }

    public BookItemRequestNotFoundException(Long id) {
        super("Message.RESERVATION_NOT_FOUND_ID.getMessage(id)");
    }
}
