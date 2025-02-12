package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.NotificationEventListenerPort;
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
