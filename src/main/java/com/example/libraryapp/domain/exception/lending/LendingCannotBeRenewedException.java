package com.example.libraryapp.domain.exception.lending;

public class LendingCannotBeRenewedException extends RuntimeException {
    public LendingCannotBeRenewedException(String message) {
        super(message);
    }
}
