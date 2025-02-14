package com.example.libraryapp.domain.useractivity.types.notify;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.notification.NotificationSentEvent;

public class EmailNotificationSentActivity extends NotificationSentActivity {

    public EmailNotificationSentActivity(NotificationSentEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getNotificationSubject());
        this.type = UserActivityType.NOTIFICATION_EMAIL;
        this.message = msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_EMAIL, event.getNotificationSubject()
        );
    }
}
