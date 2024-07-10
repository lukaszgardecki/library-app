package com.example.libraryapp.domain.exception.lending;

import com.example.libraryapp.management.Message;

public class LendingNotFoundException extends RuntimeException {

    public LendingNotFoundException(String barcode) {
        super(Message.LENDING_NOT_FOUND_BARCODE.getMessage(barcode));
    }
    public LendingNotFoundException(Long id) {
        super(Message.LENDING_NOT_FOUND.getMessage(id));
    }
}
