package com.example.libraryapp.domain.useractivity.types.notify;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.notification.NotificationSentEvent;

public class SystemNotificationSentActivity extends NotificationSentActivity {

    public SystemNotificationSentActivity(NotificationSentEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getNotificationSubject());
        this.type = UserActivityType.NOTIFICATION_SYSTEM;
        this.message = msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_SYSTEM, event.getNotificationSubject()
        );
    }
}
