package com.example.activityservice.domain.i18n;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

//    // USER ACTIVITY
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
