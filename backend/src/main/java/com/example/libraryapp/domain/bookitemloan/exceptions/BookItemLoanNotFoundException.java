package com.example.libraryapp.domain.bookitemloan.exceptions;

public class BookItemLoanNotFoundException extends RuntimeException {

    public BookItemLoanNotFoundException() {
        super("Message.LENDING_NOT_FOUND.getMessage()");
    }
    public BookItemLoanNotFoundException(String barcode) {
        super("Message.LENDING_NOT_FOUND_BARCODE.getMessage(barcode)");
    }
    public BookItemLoanNotFoundException(Long id) {
        super("Message.LENDING_NOT_FOUND.getMessage(id)");
    }
}
