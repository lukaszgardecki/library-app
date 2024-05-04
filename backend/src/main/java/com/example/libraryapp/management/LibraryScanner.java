package com.example.libraryapp.management;

import com.example.libraryapp.domain.exception.libraryscanner.LibraryScannerException;

public class LibraryScanner {

    public String scanBarcode(String bookBarcode) {
        String startValue = Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE;
        if (bookBarcode.startsWith(startValue)) {
            return bookBarcode;
        }
        else throw new LibraryScannerException(Message.BAD_BOOK_BARCODE);
    }

    public String scanCard(String cardNum) {
        String startValue = Constants.LIBRARY_NUM + Constants.CARD_START_CODE;
        if (cardNum.startsWith(startValue)) {
            return cardNum;
        }
        else throw new LibraryScannerException(Message.BAD_CARD_NUMBER);
    }
}
