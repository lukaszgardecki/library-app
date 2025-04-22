package com.example.libraryapp.domain.notification.types.user;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationContent;
import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;

public class UserRegisteredNotification extends Notification {

    public UserRegisteredNotification(UserRegisteredEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = NotificationType.USER_REGISTERED;
        this.subject = new NotificationSubject(msgProvider.getMessage(MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT));
        this.content = new NotificationContent(
                msgProvider.getMessage(MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT, event.getUserFirstName().value())
        );
    }
}
