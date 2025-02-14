package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.*;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.types.bookitem.*;
import com.example.libraryapp.domain.notification.types.user.UserRegisteredNotification;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

class NotificationFactory {
    private static final Map<Class<? extends CustomEvent>, BiFunction<CustomEvent, MessageProviderPort, Notification>> notifications = new HashMap<>();

    static {
        notifications.put(UserRegisteredEvent.class, (event, msgProvider) -> new UserRegisteredNotification((UserRegisteredEvent) event, msgProvider));
        notifications.put(BookItemLoanedEvent.class, (event, msgProvider) -> new BookItemBorrowedNotification((BookItemLoanedEvent) event, msgProvider));

        notifications.put(BookItemRenewedEvent.class, (event, msgProvider) -> new BookItemLoanRenewedNotification((BookItemRenewedEvent) event, msgProvider));
        notifications.put(BookItemReturnedEvent.class, (event, msgProvider) -> new BookItemReturnedNotification((BookItemReturnedEvent) event, msgProvider));
        notifications.put(BookItemAvailableToLoanEvent.class, (event, msgProvider) -> new BookItemAvailableToBorrowNotification((BookItemAvailableToLoanEvent) event, msgProvider));
        notifications.put(BookItemLostEvent.class, (event, msgProvider) -> new BookItemLostNotification((BookItemLostEvent) event, msgProvider));
        notifications.put(BookItemRenewalImpossibleEvent.class, (event, msgProvider) -> new RenewalImpossibleNotification((BookItemRenewalImpossibleEvent) event, msgProvider));

        notifications.put(BookItemRequestedEvent.class, (event, msgProvider) -> new BookItemRequestCreatedNotification((BookItemRequestedEvent) event, msgProvider));
        notifications.put(BookItemReservedEvent.class, (event, msgProvider) -> new BookItemReservedNotification((BookItemReservedEvent) event, msgProvider));
        notifications.put(BookItemRequestCanceledEvent.class, (event, msgProvider) -> new BookItemRequestCancelledNotification((BookItemRequestCanceledEvent) event, msgProvider));
        notifications.put(BookItemRequestReadyEvent.class, (event, msgProvider) -> new BookItemRequestCompletedNotification((BookItemRequestReadyEvent) event, msgProvider));
    }

    public static Notification createNotification(CustomEvent event, MessageProviderPort msgProvider) {
        BiFunction<CustomEvent, MessageProviderPort, Notification> strategyFunction = notifications.get(event.getClass());
        if (Objects.isNull(strategyFunction)) throw new IllegalArgumentException("Strategy not found");
        return strategyFunction.apply(event, msgProvider);
    }

    public static List<Class<? extends CustomEvent>> getKeys() {
        return notifications.keySet().stream().toList();
    }
}
