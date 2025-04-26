package com.example.notificationservice.domain;

public enum MessageKey {

    // MAIN
//    ACCESS_DENIED("access-denied"),
//    FORBIDDEN("forbidden"),
//    BODY_MISSING("body-missing"),

    // VALIDATION
//    VALIDATION_EMAIL_UNIQUE("validation.email.unique"),
//    VALIDATION_BAD_CREDENTIALS("validation.bad-credentials"),

    // TOKEN
//    TOKEN_NOT_FOUND_HASH("token.not-found.hash"),

    // NOTIFICATION
    NOTIFICATION_NOT_FOUND_ID("notification.not-found.id"),

    NOTIFICATION_USER_REGISTERED_SUBJECT("notification.user.registered.subject"),
    NOTIFICATION_USER_REGISTERED_CONTENT("notification.user.registered.content"),

    NOTIFICATION_REQUEST_CREATED_SUBJECT("notification.request.created.subject"),
    NOTIFICATION_REQUEST_CREATED_CONTENT("notification.request.created.content"),

    NOTIFICATION_REQUEST_READY_SUBJECT("notification.request.completed.subject"),
    NOTIFICATION_REQUEST_READY_CONTENT("notification.request.completed.content"),

    NOTIFICATION_REQUEST_CANCELED_SUBJECT("notification.request.cancelled.subject"),
    NOTIFICATION_REQUEST_CANCELED_CONTENT("notification.request.cancelled.content"),

    NOTIFICATION_LOAN_CREATED_SUBJECT("notification.book.borrowed.subject"),
    NOTIFICATION_LOAN_CREATED_CONTENT("notification.book.borrowed.content"),

    NOTIFICATION_REQUEST_AVAILABLE_T0_LOAN_SUBJECT("notification.book.available.subject"),
    NOTIFICATION_REQUEST_AVAILABLE_TO_LOAN_CONTENT("notification.book.available.content"),

    NOTIFICATION_RESERVATION_CREATED_SUBJECT("notification.book.reserved.subject"),
    NOTIFICATION_RESERVATION_CREATED_CONTENT_1("notification.book.reserved.content"),

//    NOTIFICATION_BOOK_RESERVED_QUEUE_UPDATE_SUBJECT("notification.book.reserved.queue-update.subject"),
//    NOTIFICATION_BOOK_RESERVED_QUEUE_UPDATE_CONTENT("notification.book.reserved.queue-update.content"),

    NOTIFICATION_LOAN_PROLONGED_SUBJECT("notification.book.renewed.subject"),
    NOTIFICATION_LOAN_PROLONGED_CONTENT("notification.book.renewed.content"),

    NOTIFICATION_BOOK_RETURNED_SUBJECT("notification.book.returned.subject"),
    NOTIFICATION_BOOK_RETURNED_CONTENT("notification.book.returned.content"),

    NOTIFICATION_BOOK_LOST_SUBJECT("notification.book.lost.subject"),
    NOTIFICATION_BOOK_LOST_CONTENT("notification.book.lost.content"),

    NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_SUBJECT("notification.book.renewal.impossible.subject"),
    NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_CONTENT("notification.book.renewal.impossible.content");

    // USER ACTIVITY
//    ACTIVITY_NOT_FOUND_ID("activity.not-found.id"),
//    ACTIVITY_REGISTER("activity.register"),
//    ACTIVITY_LOGIN_SUCCEEDED("activity.login.succeeded"),
//    ACTIVITY_LOGIN_FAILED("activity.login.failed"),
//    ACTIVITY_LOGOUT("activity.logout"),
//    ACTIVITY_REQUEST_CREATED("activity.request.created"),
//    ACTIVITY_REQUEST_CANCELLED("activity.request.cancelled"),
//    ACTIVITY_REQUEST_COMPLETED("activity.request.completed"),
//    ACTIVITY_BOOK_ITEM_BORROWED("activity.book-item.borrowed"),
//    ACTIVITY_BOOK_ITEM_BORROWED_ON_SITE("activity.book-item.borrowed-on-site"),
//    ACTIVITY_BOOK_ITEM_RESERVED_FIRST_PERSON("activity.book-item.reserved.first-person"),
//    ACTIVITY_BOOK_ITEM_RESERVED_QUEUE("activity.book-item.reserved.queue"),
//    ACTIVITY_BOOK_ITEM_RENEWED("activity.book-item.renewed"),
//    ACTIVITY_BOOK_ITEM_RETURNED("activity.book-item.returned"),
//    ACTIVITY_BOOK_ITEM_LOST("activity.book-item.lost"),
//    ACTIVITY_NOTIFICATION_SENT_SYSTEM("activity.notification-sent.system"),
//    ACTIVITY_NOTIFICATION_SENT_EMAIL("activity.notification-sent.email"),
//    ACTIVITY_NOTIFICATION_SENT_SMS("activity.notification-sent.sms");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
