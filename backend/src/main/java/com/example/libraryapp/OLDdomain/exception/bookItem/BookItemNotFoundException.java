package com.example.libraryapp.OLDdomain.exception.bookItem;

import com.example.libraryapp.OLDmanagement.Message;

public class BookItemNotFoundException extends RuntimeException {

    public BookItemNotFoundException(Long id) {
        super("Message.BOOK_ITEM_NOT_FOUND_ID.getMessage(id)");
    }

    public BookItemNotFoundException(String barcode) {
        super("Message.BOOK_ITEM_NOT_FOUND_BARCODE.getMessage(barcode)");
    }
}
