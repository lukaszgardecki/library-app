package com.example.libraryapp.domain.exception.libraryscanner;

public class InvalidBarcodeException extends RuntimeException {
    public InvalidBarcodeException(String message) {
        super(message);
    }
}
