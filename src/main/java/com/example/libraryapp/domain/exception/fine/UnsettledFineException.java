package com.example.libraryapp.domain.exception.fine;

public class UnsettledFineException extends RuntimeException{
    public UnsettledFineException(String message) {
        super(message);
    }
}
