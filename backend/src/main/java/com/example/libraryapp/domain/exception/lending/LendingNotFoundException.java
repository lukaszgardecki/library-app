package com.example.libraryapp.domain.exception.lending;

import com.example.libraryapp.management.Message;

public class LendingNotFoundException extends RuntimeException {

    public LendingNotFoundException(String barcode) {
        super(String.format(Message.LENDING_NOT_FOUND_BY_BARCODE, barcode));
    }
    public LendingNotFoundException(Long id) {
        super(String.format(Message.LENDING_NOT_FOUND,id));
    }
}
