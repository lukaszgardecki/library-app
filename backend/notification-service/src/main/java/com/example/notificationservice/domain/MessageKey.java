package com.example.notificationservice.domain;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // NOTIFICATION
    NOTIFICATION_NOT_FOUND_ID("notification.not-found.id"),

    NOTIFICATION_USER_REGISTERED_SUBJECT("notification.user.registered.subject"),
    NOTIFICATION_USER_REGISTERED_CONTENT("notification.user.registered.content"),

    NOTIFICATION_REQUEST_CREATED_SUBJECT("notification.request.created.subject"),
    NOTIFICATION_REQUEST_CREATED_CONTENT("notification.request.created.content"),

    NOTIFICATION_REQUEST_READY_SUBJECT("notification.request.ready.subject"),
    NOTIFICATION_REQUEST_READY_CONTENT("notification.request.ready.content"),

    NOTIFICATION_REQUEST_CANCELED_SUBJECT("notification.request.cancelled.subject"),
    NOTIFICATION_REQUEST_CANCELED_CONTENT("notification.request.cancelled.content"),

    NOTIFICATION_REQUEST_AVAILABLE_T0_LOAN_SUBJECT("notification.request.available.subject"),
    NOTIFICATION_REQUEST_AVAILABLE_TO_LOAN_CONTENT("notification.request.available.content"),

    NOTIFICATION_RESERVATION_CREATED_SUBJECT("notification.reservation.created.subject"),
    NOTIFICATION_RESERVATION_CREATED_CONTENT_1("notification.reservation.created.content"),

    NOTIFICATION_LOAN_CREATED_SUBJECT("notification.loan.created.subject"),
    NOTIFICATION_LOAN_CREATED_CONTENT("notification.loan.created.content"),

    NOTIFICATION_LOAN_PROLONGED_SUBJECT("notification.loan.prolonged.subject"),
    NOTIFICATION_LOAN_PROLONGED_CONTENT("notification.loan.prolonged.content"),

    NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_SUBJECT("notification.loan.prolongation.not-allowed.subject"),
    NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_CONTENT("notification.loan.prolongation.not-allowed.content"),

    NOTIFICATION_BOOK_RETURNED_SUBJECT("notification.book.returned.subject"),
    NOTIFICATION_BOOK_RETURNED_CONTENT("notification.book.returned.content"),

    NOTIFICATION_BOOK_LOST_SUBJECT("notification.book.lost.subject"),
    NOTIFICATION_BOOK_LOST_CONTENT("notification.book.lost.content");



    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
