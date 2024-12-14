package com.example.libraryapp.NEWapplication.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.bookitem.BookItemBorrowedActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.bookitem.BookItemRenewedActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.bookitem.BookItemReturnedActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.bookitem.RequestCreatedActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.notify.EmailNotificationSentActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.notify.SmsNotificationSentActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.notify.SystemNotificationSentActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.user.AccountCreatedActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.user.LoginFailedActivity;
import com.example.libraryapp.NEWdomain.useractivity.types.user.LoginSuccessActivity;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemLoanedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRenewedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemReturnedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.EmailNotificationSentEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.SmsNotificationSentEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.SystemNotificationSentEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserAuthFailedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserAuthSuccessEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserRegisteredEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

class ActivityFactory {
    private static final Map<Class<? extends CustomEvent>, Function<CustomEvent, UserActivity>> activities;

    static {
        activities = Map.of(
                UserRegisteredEvent.class, event -> new AccountCreatedActivity((UserRegisteredEvent) event),
                UserAuthSuccessEvent.class, event -> new LoginFailedActivity((UserAuthSuccessEvent) event),
                UserAuthFailedEvent.class, event -> new LoginSuccessActivity((UserAuthFailedEvent) event),

                BookItemRequestedEvent.class, event -> new RequestCreatedActivity((BookItemRequestedEvent) event),
                BookItemLoanedEvent.class, event -> new BookItemBorrowedActivity((BookItemLoanedEvent) event),
                BookItemRenewedEvent.class, event -> new BookItemRenewedActivity((BookItemRenewedEvent) event),
                BookItemReturnedEvent.class, event -> new BookItemReturnedActivity((BookItemReturnedEvent) event),

                SystemNotificationSentEvent.class, event -> new SystemNotificationSentActivity((SystemNotificationSentEvent) event),
                SmsNotificationSentEvent.class, event -> new SmsNotificationSentActivity((SmsNotificationSentEvent) event),
                EmailNotificationSentEvent.class, event -> new EmailNotificationSentActivity((EmailNotificationSentEvent) event)
        );
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
