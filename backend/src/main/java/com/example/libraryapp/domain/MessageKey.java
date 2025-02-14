package com.example.libraryapp.domain;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // VALIDATION
    VALIDATION_EMAIL_UNIQUE("validation.email.unique"),
    VALIDATION_BAD_CREDENTIALS("validation.bad-credentials"),

    // PERSON
    PERSON_NOT_FOUND_ID("person.not-found.id"),

    // USER
    USER_NOT_FOUND("user.not-found"),
    USER_NOT_FOUND_ID("user.not-found.id"),
    USER_NOT_RETURNED_BOOKS("user.not-returned-books"),
    USER_UNSETTLED_CHARGES("user.unsettled-charges"),

    // BOOK
    BOOK_NOT_FOUND_ID("book.not-found.id"),

    // BOOK ITEM
    BOOK_ITEM_NOT_FOUND_ID("book-item.not-found.id"),
    BOOK_ITEM_ALREADY_REQUESTED("book-item.already-requested"),
    BOOK_ITEM_DELETION_FAILED("book-item.deletion-failed"),

    // BOOK ITEM LOAN
    LOAN_NOT_FOUND_ID("loan.not-found.id"),
    LOAN_LIMIT_EXCEEDED("loan.limit-exceeded"),
    LOAN_RENEWAL_FAILED_RETURN_DATE("loan.renewal-failed.return-date"),

    // BOOK ITEM REQUEST
    REQUEST_NOT_FOUND("request.not-found"),
    REQUEST_NOT_FOUND_ID("request.not-found.id"),
    REQUEST_ALREADY_CREATED("request.already-created"),
    REQUEST_STATUS_NOT_READY("request.status.not-ready"),
    REQUEST_LIMIT_EXCEEDED("request.limit-exceeded"),
    REQUEST_CREATION_FAILED_BOOK_ITEM_LOST("request.creation-failed.book-item-lost"),

    // RACK
    RACK_NOT_FOUND_ID("rack.not-found.id"),
    RACK_NOT_FOUND_LOCATION("rack.not-found.location"),
    RACK_LOCATION_ALREADY_EXISTS("rack.location.already-exists"),
    RACK_DELETION_FAILED("rack.deletion-failed"),

    // CARD
    CARD_NOT_FOUND_ID("card.not-found.id"),

    // FINE
    FINE_NOT_FOUND_ID("fine.not-found.id"),
    FINE_ALREADY_PAID("fine.already-paid"),

    // PAYMENT
    PAYMENT_NOT_FOUND_ID("payment.not-found.id"),

    // NOTIFICATION
    NOTIFICATION_NOT_FOUND_ID("notification.not-found.id"),

    NOTIFICATION_USER_REGISTERED_SUBJECT("notification.user.registered.subject"),
    NOTIFICATION_USER_REGISTERED_CONTENT("notification.user.registered.content"),

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
    NOTIFICATION_BOOK_RESERVED_CONTENT_FIRST_PERSON("notification.book.reserved.content"),

    NOTIFICATION_BOOK_RESERVED_QUEUE_UPDATE_SUBJECT("notification.book.reserved.queue-update.subject"),
    NOTIFICATION_BOOK_RESERVED_QUEUE_UPDATE_CONTENT("notification.book.reserved.queue-update.content"),

    NOTIFICATION_BOOK_RENEWED_SUBJECT("notification.book.renewed.subject"),
    NOTIFICATION_BOOK_RENEWED_CONTENT("notification.book.renewed.content"),

    NOTIFICATION_BOOK_RETURNED_SUBJECT("notification.book.returned.subject"),
    NOTIFICATION_BOOK_RETURNED_CONTENT("notification.book.returned.content"),

    NOTIFICATION_BOOK_LOST_SUBJECT("notification.book.lost.subject"),
    NOTIFICATION_BOOK_LOST_CONTENT("notification.book.lost.content"),

    NOTIFICATION_RENEWAL_IMPOSSIBLE_SUBJECT("notification.book.renewal.impossible.subject"),
    NOTIFICATION_RENEWAL_IMPOSSIBLE_CONTENT("notification.book.renewal.impossible.content"),

    // USER ACTIVITY
    ACTIVITY_NOT_FOUND_ID("activity.not-found.id"),
    ACTIVITY_REGISTER("activity.register"),
    ACTIVITY_LOGIN_SUCCEEDED("activity.login.succeeded"),
    ACTIVITY_LOGIN_FAILED("activity.login.failed"),
    ACTIVITY_LOGOUT("activity.logout"),
    ACTIVITY_REQUEST_CREATED("activity.request.created"),
    ACTIVITY_REQUEST_CANCELLED("activity.request.cancelled"),
    ACTIVITY_REQUEST_COMPLETED("activity.request.completed"),
    ACTIVITY_BOOK_ITEM_BORROWED("activity.book-item.borrowed"),
    ACTIVITY_BOOK_ITEM_BORROWED_ON_SITE("activity.book-item.borrowed-on-site"),
    ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON("activity.book-item.reserved.first-person"),
    ACTIVITY_BOOK_ITEM_RESERVED_QUEUE("activity.book-item.reserved.queue"),
    ACTIVITY_BOOK_ITEM_RENEWED("activity.book-item.renewed"),
    ACTIVITY_BOOK_ITEM_RETURNED("activity.book-item.returned"),
    ACTIVITY_BOOK_ITEM_LOST("activity.book-item.lost"),
    ACTIVITY_NOTIFICATION_SENT_SYSTEM("activity.notification-sent.system"),
    ACTIVITY_NOTIFICATION_SENT_EMAIL("activity.notification-sent.email"),
    ACTIVITY_NOTIFICATION_SENT_SMS("activity.notification-sent.sms");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
