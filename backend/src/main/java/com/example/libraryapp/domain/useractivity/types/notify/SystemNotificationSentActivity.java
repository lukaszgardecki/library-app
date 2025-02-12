package com.example.libraryapp.domain.useractivity.types.notify;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.notification.NotificationSentEvent;

public class SystemNotificationSentActivity extends NotificationSentActivity {

    public SystemNotificationSentActivity(NotificationSentEvent event) {
        super(event.getUserId(), event.getNotificationSubject());
        this.type = UserActivityType.NOTIFICATION_SYSTEM;
        this.message = "Message.ACTION_NOTIFICATION_SENT_SYSTEM.getMessage(notification.getSubject())";
    }

//    public SystemNotificationSentActivity(Long userId, String notificationSubject) {
//        super(userId);
//        this.type = UserActivityType.NOTIFICATION_SYSTEM;
//        this.message = "Message.ACTION_NOTIFICATION_SENT_SYSTEM.getMessage(notification.getSubject())";
//    }
}
