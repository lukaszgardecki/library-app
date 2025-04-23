package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.bookitem.BookItemBarcode;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.Constants;

class BookItemBarcodeGenerator {

    public BookItemBarcode generateBarcode(BookItemId bookId) {
        int userIdLength = String.valueOf(bookId.value()).length();
        int leadZeros = Constants.BARCODE_NUM_LENGTH - userIdLength;
        return new BookItemBarcode(
                Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE + "0".repeat(leadZeros) + bookId.value()
        );
    }
}
