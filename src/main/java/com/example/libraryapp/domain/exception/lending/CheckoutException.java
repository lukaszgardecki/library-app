package com.example.libraryapp.domain.exception.lending;

public class CheckoutException extends RuntimeException {
    public CheckoutException(String message) {
        super(message);
    }
}
