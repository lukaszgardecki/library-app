package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.ports.NotificationEventListenerPort;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class NotificationEventListenerImpl implements NotificationEventListenerPort {
    private final NotificationSender sender;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return NotificationFactory.getKeys();
    }

    @Override
    public void onEvent(CustomEvent event) {
        Notification notification = NotificationFactory.createNotification(event);
        sender.send(notification);
    }
}
