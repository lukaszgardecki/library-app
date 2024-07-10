package com.example.libraryapp.management;

import org.springframework.context.MessageSource;

import java.util.Locale;

public enum Message {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // MEMBER
    MEMBER_NOT_FOUND("member.not-found"),
    MEMBER_NOT_RETURNED_BOOKS("member.not-returned-books"),
    MEMBER_UNSETTLED_CHARGES("member.unsettled-charges"),

    // BOOK
    BOOK_NOT_FOUND("book.not-found"),

    // BOOK ITEM
    BOOK_ITEM_NOT_FOUND_ID("book-item.not-found.id"),
    BOOK_ITEM_NOT_FOUND_BARCODE("book-item.not-found.barcode"),
    BOOK_ITEM_DELETION_FAILED("book-item.deletion-failed"),

    // LENDING
    LENDING_NOT_FOUND("lending.not-found"),
    LENDING_NOT_FOUND_BARCODE("lending.not-found.barcode"),
    LENDING_LIMIT_EXCEEDED("lending.limit-exceeded"),
    LENDING_RENEWAL_FAILED("lending.renewal-failed"),

    // RESERVATION
    RESERVATION_NOT_FOUND("reservation.not-found"),
    RESERVATION_NOT_FOUND_ID("reservation.not-found.id"),
    RESERVATION_ALREADY_CREATED("reservation.already-created"),
    RESERVATION_LIMIT_EXCEEDED("reservation.limit-exceeded"),
    RESERVATION_CREATION_FAILED_BOOK_ITEM_LOST("reservation.creation-failed.book-item-lost"),
    RESERVATION_CANCELLATION_BOOK_ITEM_LOST("reservation.cancellation.book-item-lost"),

    // RACK
    RACK_NOT_FOUND("rack.not-found"),
    RACK_LOCATION_ALREADY_EXISTS("rack.location.already-exists"),
    RACK_DELETION_FAILED("rack.deletion-failed"),

    // CARD
    CARD_NOT_FOUND("card.not-found"),

    // PAYMENT
    PAYMENT_NOT_FOUND("payment.not-found"),

    // VALIDATION
    VALIDATION_BOOK_BARCODE("validation.book.barcode"),
    VALIDATION_CARD_NUMBER("validation.card.number"),
    VALIDATION_EMAIL_UNIQUE("validation.email.unique"),
    VALIDATION_BAD_CREDENTIALS("validation.bad-credentials"),

    // NOTIFICATION
    NOTIFICATION_NOT_FOUND("notification.not-found"),

    NOTIFICATION_REQUEST_CREATED_SUBJECT("notification.request.created.subject"),
    NOTIFICATION_REQUEST_CREATED_CONTENT("notification.request.created.content"),

    NOTIFICATION_REQUEST_COMPLETED_SUBJECT("notification.request.completed.subject"),
    NOTIFICATION_REQUEST_COMPLETED_CONTENT("notification.request.completed.content"),

    NOTIFICATION_REQUEST_CANCELLED_SUBJECT("notification.request.cancelled.subject"),
    NOTIFICATION_REQUEST_CANCELLED_CONTENT("notification.request.cancelled.content"),

    NOTIFICATION_BOOK_BORROWED_SUBJECT("notification.book.borrowed.subject"),
    NOTIFICATION_BOOK_BORROWED_CONTENT("notification.book.borrowed.content"),

    NOTIFICATION_BOOK_AVAILABLE_SUBJECT("notification.book.available.subject"),
    NOTIFICATION_BOOK_AVAILABLE_CONTENT("notification.book.available.content"),

    NOTIFICATION_BOOK_RESERVED_SUBJECT("notification.book.reserved.subject"),
    NOTIFICATION_BOOK_RESERVED_CONTENT_FIRST_PERSON("notification.book.reserved.content.first-person"),
    NOTIFICATION_BOOK_RESERVED_CONTENT_QUEUE_1_AHEAD("notification.book.reserved.content.queue-1-ahead"),
    NOTIFICATION_BOOK_RESERVED_CONTENT_QUEUE_2_AHEAD("notification.book.reserved.content.queue-2-ahead"),

    NOTIFICATION_BOOK_RENEWED_SUBJECT("notification.book.renewed.subject"),
    NOTIFICATION_BOOK_RENEWED_CONTENT("notification.book.renewed.content"),

    NOTIFICATION_BOOK_RETURNED_SUBJECT("notification.book.returned.subject"),
    NOTIFICATION_BOOK_RETURNED_CONTENT("notification.book.returned.content"),

    NOTIFICATION_BOOK_LOST_SUBJECT("notification.book.lost.subject"),
    NOTIFICATION_BOOK_LOST_CONTENT("notification.book.lost.content"),

    NOTIFICATION_RENEWAL_IMPOSSIBLE_SUBJECT("notification.book.renewal.impossible.subject"),
    NOTIFICATION_RENEWAL_IMPOSSIBLE_CONTENT("notification.book.renewal.impossible.content"),

    // ACTION
    ACTION_NOT_FOUND_ID("action.not-found.id"),
    ACTION_REGISTER("action.register"),
    ACTION_LOGIN_SUCCEEDED("action.login.succeeded"),
    ACTION_LOGIN_FAILED("action.login.failed"),
    ACTION_LOGOUT("action.logout"),
    ACTION_REQUEST_CREATED("action.request.created"),
    ACTION_REQUEST_SENT("action.request.sent"),
    ACTION_REQUEST_CANCELLED("action.request.cancelled"),
    ACTION_REQUEST_COMPLETED("action.request.completed"),
    ACTION_BOOK_BORROWED("action.book.borrowed"),
    ACTION_BOOK_BORROWED_ON_SITE("action.book.borrowed-on-site"),
    ACTION_BOOK_RESERVED_FIRST_PERSON("action.book.reserved.first-person"),
    ACTION_BOOK_RESERVED_QUEUE("action.book.reserved.queue"),
    ACTION_BOOK_RENEWED("action.book.renewed"),
    ACTION_BOOK_RETURNED("action.book.returned"),
    ACTION_BOOK_LOST("action.book.lost"),
    ACTION_NOTIFICATION_SENT_EMAIL("action.notification-sent.email"),
    ACTION_NOTIFICATION_SENT_SMS("action.notification-sent.sms"),
    ACTION_NOTIFICATION_SENT_SYSTEM("action.notification-sent.system");

    private final String key;
    private static MessageSource messageSource;

    Message(String key) {
        this.key = key;
    }

    public static void setMessageSource(MessageSource messageSource) {
        Message.messageSource = messageSource;
    }

    public String getMessage(Object... params) {
        return getMessage(Locale.getDefault(), params);
    }

    public String getMessage(Locale locale, Object... params) {
        return messageSource.getMessage(key, params, locale);
    }
}
