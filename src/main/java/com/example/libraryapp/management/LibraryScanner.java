package com.example.libraryapp.management;

import com.example.libraryapp.domain.exception.libraryscanner.InvalidBarcodeException;
import com.example.libraryapp.domain.exception.libraryscanner.InvalidCardNumberException;

public class LibraryScanner {

    public String scanBarcode(String bookBarcode) {
        String startValue = Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE;
        if (bookBarcode.startsWith(startValue)) {
            return bookBarcode;
        }
        else throw new InvalidBarcodeException("Invalid Barcode.");
    }

    public String scanCard(String cardNum) {
        String startValue = Constants.LIBRARY_NUM + Constants.CARD_START_CODE;
        if (cardNum.startsWith(startValue)) {
            return cardNum;
        }
        else throw new InvalidCardNumberException("Invalid card number.");
    }
}
