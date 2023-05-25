package com.example.libraryapp.domain.helper;

public class CardNumGenerator {
    private static final int NUM_LENGTH = 8;

    public static String generate(Long userId) {
        int userIdLength = String.valueOf(userId).length();
        int leadZeros = NUM_LENGTH - userIdLength;
        return "0".repeat(leadZeros) + userId;
    }
}
