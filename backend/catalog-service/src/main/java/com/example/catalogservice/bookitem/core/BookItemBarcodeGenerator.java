package com.example.catalogservice.bookitem.core;

import com.example.catalogservice.bookitem.domain.model.BookItemBarcode;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.common.Constants;

class BookItemBarcodeGenerator {

    public BookItemBarcode generateBarcode(BookItemId bookId) {
        int userIdLength = String.valueOf(bookId.value()).length();
        int leadZeros = Constants.BARCODE_NUM_LENGTH - userIdLength;
        return new BookItemBarcode(
                Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE + "0".repeat(leadZeros) + bookId.value()
        );
    }
}
