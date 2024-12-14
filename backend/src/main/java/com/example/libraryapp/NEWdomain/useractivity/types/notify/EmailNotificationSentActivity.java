package com.example.libraryapp.NEWdomain.useractivity.types.notify;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.NotificationSentEvent;

public class EmailNotificationSentActivity extends NotificationSentActivity {

    public EmailNotificationSentActivity(NotificationSentEvent event) {
        super(event.getUserId(), event.getNotificationSubject());
        this.type = UserActivityType.NOTIFICATION_EMAIL;
        this.message = "Message.ACTION_NOTIFICATION_SENT_EMAIL.getMessage(notification.getSubject())";
    }

//    public EmailNotificationSentActivity(Long userId, String notificationSubject) {
//        super(userId);
//        this.type = UserActivityType.NOTIFICATION_EMAIL;
//        this.message = "Message.ACTION_NOTIFICATION_SENT_EMAIL.getMessage(notification.getSubject())";
//    }
}
