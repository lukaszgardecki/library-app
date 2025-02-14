package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.*;
import com.example.libraryapp.domain.event.types.notification.EmailNotificationSentEvent;
import com.example.libraryapp.domain.event.types.notification.SmsNotificationSentEvent;
import com.example.libraryapp.domain.event.types.notification.SystemNotificationSentEvent;
import com.example.libraryapp.domain.event.types.user.UserAuthFailedEvent;
import com.example.libraryapp.domain.event.types.user.UserAuthSuccessEvent;
import com.example.libraryapp.domain.event.types.user.UserLogoutEvent;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.types.bookitem.*;
import com.example.libraryapp.domain.useractivity.types.notify.EmailNotificationSentActivity;
import com.example.libraryapp.domain.useractivity.types.notify.SmsNotificationSentActivity;
import com.example.libraryapp.domain.useractivity.types.notify.SystemNotificationSentActivity;
import com.example.libraryapp.domain.useractivity.types.user.AccountCreatedActivity;
import com.example.libraryapp.domain.useractivity.types.user.LoginFailedActivity;
import com.example.libraryapp.domain.useractivity.types.user.LoginSuccessActivity;
import com.example.libraryapp.domain.useractivity.types.user.LogoutActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

class ActivityFactory {
    private static final Map<Class<? extends CustomEvent>, BiFunction<CustomEvent, MessageProviderPort, UserActivity>> activities = new HashMap<>();

    static {
        activities.put(UserRegisteredEvent.class, (event, msgProvider) -> new AccountCreatedActivity((UserRegisteredEvent) event, msgProvider));
        activities.put(UserAuthSuccessEvent.class, (event, msgProvider) -> new LoginSuccessActivity((UserAuthSuccessEvent) event, msgProvider));
        activities.put(UserAuthFailedEvent.class, (event, msgProvider) -> new LoginFailedActivity((UserAuthFailedEvent) event, msgProvider));
        activities.put(UserLogoutEvent.class, (event, msgProvider) -> new LogoutActivity((UserLogoutEvent) event, msgProvider));

        activities.put(BookItemRequestedEvent.class, (event, msgProvider) -> new RequestCreatedActivity((BookItemRequestedEvent) event, msgProvider));
        activities.put(BookItemLoanedEvent.class, (event, msgProvider) -> new BookItemBorrowedActivity((BookItemLoanedEvent) event, msgProvider));
        activities.put(BookItemRenewedEvent.class, (event, msgProvider) -> new BookItemRenewedActivity((BookItemRenewedEvent) event, msgProvider));
        activities.put(BookItemReturnedEvent.class, (event, msgProvider) -> new BookItemReturnedActivity((BookItemReturnedEvent) event, msgProvider));
        activities.put(BookItemReservedEvent.class, (event, msgProvider) -> new BookItemReservedActivity((BookItemReservedEvent) event, msgProvider));
        activities.put(BookItemLostEvent.class, (event, msgProvider) -> new BookItemLostActivity((BookItemLostEvent) event, msgProvider));

        activities.put(BookItemRequestCanceledEvent.class, (event, msgProvider) -> new RequestCancelActivity((BookItemRequestCanceledEvent) event, msgProvider));
        activities.put(BookItemRequestReadyEvent.class, (event, msgProvider) -> new RequestCompletedActivity((BookItemRequestReadyEvent) event, msgProvider));

        activities.put(SystemNotificationSentEvent.class, (event, msgProvider) -> new SystemNotificationSentActivity((SystemNotificationSentEvent) event, msgProvider));
        activities.put(EmailNotificationSentEvent.class, (event, msgProvider) -> new EmailNotificationSentActivity((EmailNotificationSentEvent) event, msgProvider));
        activities.put(SmsNotificationSentEvent.class, (event, msgProvider) -> new SmsNotificationSentActivity((SmsNotificationSentEvent) event, msgProvider));
    }


    public static UserActivity createActivity(CustomEvent event, MessageProviderPort messageProvider) {
        BiFunction<CustomEvent, MessageProviderPort, UserActivity> activitySupplier = activities.get(event.getClass());
        if (activitySupplier == null) {
            throw new IllegalArgumentException("No activity registered for event type: " + event.getClass());
        }
        return activitySupplier.apply(event, messageProvider);
    }

    public static List<Class<? extends CustomEvent>> getKeys() {
        return activities.keySet().stream().toList();
    }
}
