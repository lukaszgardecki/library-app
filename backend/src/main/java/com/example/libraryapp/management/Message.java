package com.example.libraryapp.management;

public class Message {
    public static final String ACCESS_DENIED = "Access Denied";
    public static final String FORBIDDEN = "Forbidden: You do not have permission to view this content.";
    public static final String BODY_MISSING = "Required request body is missing";

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
    public static final String RESERVATION_CANCEL_BOOK_ITEM_LOST = "The reservation has been cancelled because the book item was lost";

    public static final String RACK_NOT_FOUND = "No rack with id: %s";
    public static final String RACK_LOCATION_ALREADY_EXISTS = "The rack location (%s) already exists";
    public static final String RACK_CANNOT_BE_DELETED = "The rack (%s) can't be deleted. Only empty racks can be deleted";

    public static final String CARD_NOT_FOUND = "No card with id: %s";

    public static final String PAYMENT_NOT_FOUND = "No payment with id: %s";

    public static final String NOTIFICATION_NOT_FOUND = "No notification with id: %s";

    public static final String BAD_BOOK_BARCODE = "Invalid Barcode";
    public static final String BAD_CARD_NUMBER = "Invalid card number";
    public static final String BAD_EMAIL = "Email must be unique";
    public static final String BAD_CREDENTIALS = "Bad credentials";

    public static final String RESERVATION_CREATED = "The reservation has been successfully created.";
    public static final String RESERVATION_DELETED = "The reservation has been successfully cancelled.";
    public static final String RESERVATION_READY = "The reservation has been successfully completed.";
    public static final String BOOK_BORROWED = "The book has been successfully borrowed.";
    public static final String BOOK_RESERVED_FIRST_PERSON = """
            Your reservation for the book "%s" has been accepted. The book is currently on loan to another reader, and its expected return date is %s.
            
            We will notify you as soon as the book becomes available for you to borrow. Thank you for using our library services.
            """;
    public static final String BOOK_RESERVED_QUEUE = """
            Your reservation for the book "%s" has been accepted. There are %s people ahead of you in the waiting queue.
            
            We will notify you as soon as the book becomes available for you to borrow. Thank you for using our library services.
            """;
    public static final String BOOK_AVAILABLE_TO_BORROW = """
            We are pleased to inform you that the book you reserved, titled "%s", has been returned and is now available for borrowing. You can visit the library to check it out at your earliest convenience.
            
            Thank you for using our library services.
            """;
    public static final String BOOK_RENEWED = "The book has been successfully extended";
    public static final String BOOK_RETURNED = "The book has been successfully returned";
    public static final String BOOK_LOST = "The book has been lost. Your account has been charged an additional fee of %s";
    public static final String RENEWAL_IMPOSSIBLE = "The document %s has been reserved by another user. Renewal is not possible.";

    // Action templates:
    public static final String ACTION_NOT_FOUND_BY_ID = "No action with id: %s";
    public static final String ACTION_REGISTER = "Zapisanie czytelnika %s %s, Nr karty %s";
    public static final String ACTION_LOGIN = "Zalogowanie czytelnika %s %s, Nr karty %s";
    public static final String ACTION_LOGOUT = "Wylogowanie czytelnika %s %s, Nr karty %s";
    public static final String ACTION_LOGIN_FAILED = "Nieudane logowanie czytelnika %s %s, Nr karty %s";
    public static final String ACTION_REQUEST_NEW = "Zamówiono '%s'";
    public static final String ACTION_REQUEST_SENT = "Wysłano zamówienie '%s'";
    public static final String ACTION_REQUEST_CANCELED = "Anulowano zamówienie '%s'";
    public static final String ACTION_REQUEST_COMPLETED = "Zrealizowano '%s'";
    public static final String ACTION_BOOK_BORROWED = "Wypożyczono '%s'";
    public static final String ACTION_BOOK_BORROWED_ON_SITE = "Udostępniono '%s'";
    public static final String ACTION_BOOK_RESERVED_FIRST_PERSON = "Rezerwacja do dnia %s. Dokument: '%s'";
    public static final String ACTION_BOOK_RESERVED_QUEUE = "Rezerwacja dokumentu: '%s'. Pozycja w kolejce oczekujących: %s.";
    public static final String ACTION_BOOK_RENEWED = "Prolongata czytelnika '%s'";
    public static final String ACTION_BOOK_RETURNED = "Zwrócono '%s'";
    public static final String ACTION_BOOK_LOST = "Zgubiono '%s'";
    public static final String ACTION_NOTIFICATION_SENT_EMAIL = "Wysłano e-mail do czytelnika (%s)";
    public static final String ACTION_NOTIFICATION_SENT_SMS = "Wysłano wiadomość SMS do czytelnika (%s)";
    public static final String ACTION_NOTIFICATION_SENT_SYSTEM = "Wysłano powiadomienie do czytelnika (%s)";

    // Notification reasons:
    public static final String REASON_BOOK_BORROWED = "Wypożyczenie dokumentu";
    public static final String REASON_BOOK_AVAILABLE_TO_BORROW = "Dokument możliwy do wypożyczenia";
    public static final String REASON_BOOK_RESERVED = "Rezerwacja dokumentu";
    public static final String REASON_BOOK_RENEWED = "Prolongata dokumentu";
    public static final String REASON_BOOK_RETURNED = "Zwrócenie dokumentu";
    public static final String REASON_BOOK_LOST = "Zgubienie dokumentu";
    public static final String REASON_REQUEST_COMPLETED = "Zrealizowanie zamówienia";
    public static final String REASON_REQUEST_CANCELED = "Anulowanie zamówienia";
    public static final String REASON_REQUEST_CREATED = "Utworzenie zamówienia";
    public static final String REASON_RENEWAL_IMPOSSIBLE = "Utworzenie rezerwacji na wypożyczony dokument";
}
