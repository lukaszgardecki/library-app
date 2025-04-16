package com.example.libraryapp.application.bookitem;


import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.bookitem.model.BookItemBarcode;
import com.example.libraryapp.domain.bookitem.model.BookItemId;

class BookItemBarcodeGenerator {

    public BookItemBarcode generateBarcode(BookItemId bookId) {
        int userIdLength = String.valueOf(bookId.value()).length();
        int leadZeros = Constants.BARCODE_NUM_LENGTH - userIdLength;
        return new BookItemBarcode(
                Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE + "0".repeat(leadZeros) + bookId.value()
        );
    }
}
