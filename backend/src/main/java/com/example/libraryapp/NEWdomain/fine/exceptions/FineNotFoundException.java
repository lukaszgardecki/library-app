package com.example.libraryapp.NEWdomain.fine.exceptions;

public class FineNotFoundException extends RuntimeException {

    public FineNotFoundException() {
        super("?????");
    }

    public FineNotFoundException(Long id) {
        super("???");
    }
}
