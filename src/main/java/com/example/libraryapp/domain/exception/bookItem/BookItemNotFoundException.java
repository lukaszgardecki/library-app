package com.example.libraryapp.domain.exception.bookItem;

import com.example.libraryapp.management.Message;

public class BookItemNotFoundException extends RuntimeException {

    public BookItemNotFoundException(Long id) {
        super(String.format(Message.BOOK_ITEM_NOT_FOUND_BY_ID, id));
    }

    public BookItemNotFoundException(String barcode) {
        super(String.format(Message.BOOK_ITEM_NOT_FOUND_BY_BARCODE, barcode));
    }
}
