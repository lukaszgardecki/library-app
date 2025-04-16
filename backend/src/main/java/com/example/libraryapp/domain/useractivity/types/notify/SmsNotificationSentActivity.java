package com.example.libraryapp.domain.useractivity.types.notify;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.notification.NotificationSentEvent;

public class SmsNotificationSentActivity extends NotificationSentActivity {

    public SmsNotificationSentActivity(NotificationSentEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getNotificationSubject());
        this.type = UserActivityType.NOTIFICATION_SMS;
        this.message = new UserActivityMessage(msgProvider.getMessage(
                MessageKey.ACTIVITY_NOTIFICATION_SENT_SMS, event.getNotificationSubject().value()
        ));
    }
}
