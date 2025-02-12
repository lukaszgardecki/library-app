package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.*;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.types.bookitem.*;
import com.example.libraryapp.domain.useractivity.types.notify.EmailNotificationSentActivity;
import com.example.libraryapp.domain.useractivity.types.notify.SmsNotificationSentActivity;
import com.example.libraryapp.domain.useractivity.types.notify.SystemNotificationSentActivity;
import com.example.libraryapp.domain.useractivity.types.user.AccountCreatedActivity;
import com.example.libraryapp.domain.useractivity.types.user.LoginFailedActivity;
import com.example.libraryapp.domain.useractivity.types.user.LoginSuccessActivity;
import com.example.libraryapp.domain.event.types.notification.EmailNotificationSentEvent;
import com.example.libraryapp.domain.event.types.notification.SmsNotificationSentEvent;
import com.example.libraryapp.domain.event.types.notification.SystemNotificationSentEvent;
import com.example.libraryapp.domain.event.types.user.UserAuthFailedEvent;
import com.example.libraryapp.domain.event.types.user.UserAuthSuccessEvent;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class ActivityFactory {
    private static final Map<Class<? extends CustomEvent>, Function<CustomEvent, UserActivity>> activities = new HashMap<>();

    static {
        activities.put(UserRegisteredEvent.class, event -> new AccountCreatedActivity((UserRegisteredEvent) event));
        activities.put(UserAuthSuccessEvent.class, event -> new LoginSuccessActivity((UserAuthSuccessEvent) event));
        activities.put(UserAuthFailedEvent.class, event -> new LoginFailedActivity((UserAuthFailedEvent) event));

        activities.put(BookItemRequestedEvent.class, event -> new RequestCreatedActivity((BookItemRequestedEvent) event));
        activities.put(BookItemLoanedEvent.class, event -> new BookItemBorrowedActivity((BookItemLoanedEvent) event));
        activities.put(BookItemRenewedEvent.class, event -> new BookItemRenewedActivity((BookItemRenewedEvent) event));
        activities.put(BookItemReturnedEvent.class, event -> new BookItemReturnedActivity((BookItemReturnedEvent) event));
        activities.put(BookItemReservedEvent.class, event -> new BookItemReservedActivity((BookItemReservedEvent) event));
        activities.put(BookItemLostEvent.class, event -> new BookItemLostActivity((BookItemLostEvent) event));

        activities.put(SystemNotificationSentEvent.class, event -> new SystemNotificationSentActivity((SystemNotificationSentEvent) event));
        activities.put(SmsNotificationSentEvent.class, event -> new SmsNotificationSentActivity((SmsNotificationSentEvent) event));
        activities.put(EmailNotificationSentEvent.class, event -> new EmailNotificationSentActivity((EmailNotificationSentEvent) event));
    }


    public static UserActivity createActivity(CustomEvent event) {
        Function<CustomEvent, UserActivity> activitySupplier = activities.get(event.getClass());
        if (activitySupplier == null) {
            throw new IllegalArgumentException("No activity registered for event type: " + event.getClass());
        }
        return activitySupplier.apply(event);
    }

    public static List<Class<? extends CustomEvent>> getKeys() {
        return activities.keySet().stream().toList();
    }
}
