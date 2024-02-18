package com.example.libraryapp.management;

public class Message {
    public static final String ACCESS_DENIED = "Access Denied";

    public static final String MEMBER_NOT_FOUND = "No member with id: %s";
    public static final String NOT_RETURNED_BOOKS = "Member has not returned the books";
    public static final String UNSETTLED_CHARGES = "Member has unsettled charges";

    public static final String BOOK_NOT_FOUND = "No book with id: %s";

    public static final String BOOK_ITEM_NOT_FOUND_BY_ID = "No book item with id: %s";
    public static final String BOOK_ITEM_NOT_FOUND_BY_BARCODE = "No book item with barcode: %s";
    public static final String BOOK_ITEM_CANNOT_BE_DELETED = "Book item cannot be deleted";

    public static final String LENDING_NOT_FOUND = "No lending with id: %s";
    public static final String LENDING_NOT_FOUND_BY_BARCODE = "No lending with book's barcode: %s";
    public static final String LENDING_LIMIT_EXCEEDED = "The maximum number of books has been issued to the user";
    public static final String LENDING_CANNOT_BE_RENEWED = "The book is already reserved. The lending cannot be renewed.";

    public static final String RESERVATION_NOT_FOUND = "No reservation found";
    public static final String RESERVATION_NOT_FOUND_BY_ID = "No reservation with id: %s";
    public static final String RESERVATION_ALREADY_CREATED = "The reservation has already been created.";
    public static final String RESERVATION_LIMIT_EXCEEDED = "The user has already reserved maximum number of books";
    public static final String RESERVATION_BOOK_ITEM_LOST = "The reservation cannot be created because book item is lost";

    public static final String RACK_NOT_FOUND = "No rack with id: %s";
    public static final String RACK_LOCATION_ALREADY_EXISTS = "The rack location (%s) already exists";
    public static final String RACK_CANNOT_BE_DELETED = "The rack (%s) can't be deleted. Only empty racks can be deleted";

    public static final String BAD_BOOK_BARCODE = "Invalid Barcode";
    public static final String BAD_CARD_NUMBER = "Invalid card number";
    public static final String BAD_EMAIL = "Email must be unique";
    public static final String BAD_CREDENTIALS = "Bad credentials";

    public static final String RESERVATION_CREATED = "The reservation has been successfully created.";
    public static final String RESERVATION_DELETED = "The reservation has been successfully cancelled.";
    public static final String BOOK_BORROWED = "The book has been successfully borrowed.";
    public static final String BOOK_EXTENDED = "The book has been successfully extended";
    public static final String BOOK_RETURNED = "The book has been successfully returned";
}
