package com.example.libraryapp.NEWdomain.useractivity.types.notify;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.notification.NotificationSentEvent;

public class SmsNotificationSentActivity extends NotificationSentActivity {

    public SmsNotificationSentActivity(NotificationSentEvent event) {
        super(event.getUserId(), event.getNotificationSubject());
        this.type = UserActivityType.NOTIFICATION_SMS;
        this.message = "Message.ACTION_NOTIFICATION_SENT_SMS.getMessage(notification.getSubject())";
    }

//    public SmsNotificationSentActivity(Long userId, String notificationSubject) {
//        super(userId, notificationSubject);
//        this.type = UserActivityType.NOTIFICATION_SMS;
//        this.message = "Message.ACTION_NOTIFICATION_SENT_SMS.getMessage(notification.getSubject())";
//    }
}
