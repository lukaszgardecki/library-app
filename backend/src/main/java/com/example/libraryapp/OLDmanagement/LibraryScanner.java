package com.example.libraryapp.OLDmanagement;

import com.example.libraryapp.OLDdomain.exception.libraryscanner.LibraryScannerException;

public class LibraryScanner {

    public String scanBarcode(String bookBarcode) {
        String startValue = Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE;
        if (bookBarcode.startsWith(startValue)) {
            return bookBarcode;
        }
        else throw new LibraryScannerException("Message.VALIDATION_BOOK_BARCODE.getMessage()");
    }

    public String scanCard(String cardNum) {
        String startValue = Constants.LIBRARY_NUM + Constants.CARD_START_CODE;
        if (cardNum.startsWith(startValue)) {
            return cardNum;
        }
        else throw new LibraryScannerException("Message.VALIDATION_CARD_NUMBER.getMessage()");
    }
}
