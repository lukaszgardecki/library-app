package com.example.libraryapp.domain.notification.types.user;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;

public class UserRegisteredNotification extends Notification {

    public UserRegisteredNotification(UserRegisteredEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = NotificationType.USER_REGISTERED;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT);
        this.content = msgProvider.getMessage(MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT, event.getUserFirstName());
    }
}
