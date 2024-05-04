package com.example.libraryapp.domain.helper;

import com.example.libraryapp.management.Constants;

public class LibraryGenerator {

    public static String generateBarcode(Long bookId) {
        int userIdLength = String.valueOf(bookId).length();
        int leadZeros = Constants.BARCODE_NUM_LENGTH - userIdLength;
        return Constants.LIBRARY_NUM + Constants.BARCODE_START_CODE + "0".repeat(leadZeros) + bookId;
    }

    public static String generateCardNum(Long userId) {
        int userIdLength = String.valueOf(userId).length();
        int leadZeros = Constants.CARD_NUM_LENGTH - userIdLength;
        return Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "0".repeat(leadZeros) + userId;
    }
}
