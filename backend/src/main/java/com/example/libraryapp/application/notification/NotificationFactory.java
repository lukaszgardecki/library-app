package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.types.bookitem.*;
import com.example.libraryapp.domain.notification.types.user.UserRegisteredNotification;
import com.example.libraryapp.infrastructure.events.event.CustomEvent;
import com.example.libraryapp.infrastructure.events.event.bookitem.*;
import com.example.libraryapp.infrastructure.events.event.user.UserRegisteredEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

class NotificationFactory {
    private static final Map<Class<? extends CustomEvent>, Function<CustomEvent, Notification>> notifications = new HashMap<>();

    static {
        notifications.put(UserRegisteredEvent.class, event -> new UserRegisteredNotification((UserRegisteredEvent) event));
        notifications.put(BookItemLoanedEvent.class, event -> new BookBorrowedNotification((BookItemLoanedEvent) event));

        notifications.put(BookItemRenewedEvent.class, event -> new BookRenewedNotification((BookItemRenewedEvent) event));
        notifications.put(BookItemReturnedEvent.class, event -> new BookReturnedNotification((BookItemReturnedEvent) event));
        notifications.put(BookItemAvailableToLoanEvent.class, event -> new BookAvailableToBorrowNotification((BookItemAvailableToLoanEvent) event));

        notifications.put(BookItemRequestedEvent.class, event -> new RequestCreatedNotification((BookItemRequestedEvent) event));
        notifications.put(BookItemStillInQueueEvent.class, event -> new BookItemStillInQueueNotification((BookItemStillInQueueEvent) event));
        notifications.put(BookItemRequestCanceledEvent.class, event -> new RequestCancelledNotification((BookItemRequestCanceledEvent) event));
    }

    public static Notification createNotification(CustomEvent event) {
        Function<CustomEvent, Notification> strategyFunction = notifications.get(event.getClass());
        if (Objects.isNull(strategyFunction)) throw new IllegalArgumentException("Strategy not found");
        return strategyFunction.apply(event);
    }

    public static List<Class<? extends CustomEvent>> getKeys() {
        return notifications.keySet().stream().toList();
    }
}
