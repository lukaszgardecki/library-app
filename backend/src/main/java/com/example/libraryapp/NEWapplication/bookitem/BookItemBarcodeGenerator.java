package com.example.libraryapp.NEWapplication.bookitem;


import com.example.libraryapp.NEWapplication.constants.Constants;

class BookItemBarcodeGenerator {

    public String generateBarcode(Long bookId) {
        int userIdLength = String.valueOf(bookId).length();
        int leadZeros = Constants.BARCODE_NUM_LENGTH - userIdLength;
        return Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE + "0".repeat(leadZeros) + bookId;
    }
}
