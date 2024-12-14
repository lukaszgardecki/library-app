package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.types.bookitem.*;
import com.example.libraryapp.NEWdomain.notification.types.user.UserRegisteredNotification;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.*;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserRegisteredEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

class NotificationFactory {
    private static final Map<Class<? extends CustomEvent>, Function<CustomEvent, Notification>> notifictaions;

    static {
        notifictaions = Map.of(
                UserRegisteredEvent.class, event -> new UserRegisteredNotification((UserRegisteredEvent) event),
                BookItemRequestedEvent.class, event -> new RequestCreatedNotification((BookItemRequestedEvent) event),
                BookItemLoanedEvent.class, event -> new BookBorrowedNotification((BookItemLoanedEvent) event),
                BookItemRenewedEvent.class, event -> new BookRenewedNotification((BookItemRenewedEvent) event),
                BookItemReturnedEvent.class, event -> new BookReturnedNotification((BookItemReturnedEvent) event),
                BookItemAvailableToLoanEvent.class, event -> new BookAvailableToBorrowNotification((BookItemAvailableToLoanEvent) event)
        );
    }

    public static List<Class<? extends CustomEvent>> getKeys() {
        return notifictaions.keySet().stream().toList();
    }

    public static Notification createNotification(CustomEvent event) {
        Function<CustomEvent, Notification> strategyFunction = notifictaions.get(event.getClass());
        if (Objects.isNull(strategyFunction)) throw new IllegalArgumentException("Strategy not found");
        return strategyFunction.apply(event);
    }
}
