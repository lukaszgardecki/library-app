package com.example.libraryapp.domain.fine.exceptions;

public class FineAlreadyPaidException extends RuntimeException {

    public FineAlreadyPaidException() {
        super("Kara została już opłacona...");
    }

    public FineAlreadyPaidException(Long id) {
        super(String.format("Kara (id=%s) zostałą już opłacona.", id));
    }
}
