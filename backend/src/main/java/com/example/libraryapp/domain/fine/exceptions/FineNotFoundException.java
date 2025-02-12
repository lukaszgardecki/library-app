package com.example.libraryapp.domain.fine.exceptions;

public class FineNotFoundException extends RuntimeException {

    public FineNotFoundException() {
        super("?????");
    }

    public FineNotFoundException(Long id) {
        super("???");
    }
}
