package com.example.libraryapp.NEWdomain.bookitem.exceptions;

public class BookItemNotFoundException extends RuntimeException {

    public BookItemNotFoundException(Long id) {
        super("Message.BOOK_ITEM_NOT_FOUND_ID.getMessage(id)");
    }

    public BookItemNotFoundException(String barcode) {
        super("Message.BOOK_ITEM_NOT_FOUND_BARCODE.getMessage(barcode)");
    }
}
