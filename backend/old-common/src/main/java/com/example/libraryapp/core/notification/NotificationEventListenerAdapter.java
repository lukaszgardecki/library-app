package com.example.libraryapp.core.notification;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.NotificationEventListenerPort;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class NotificationEventListenerAdapter implements NotificationEventListenerPort {
    private final NotificationRepositoryPort notificationRepository;
    private final MessageProviderPort msgProvider;
    private final NotificationSender sender;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return NotificationFactory.getKeys();
    }

    @Override
    public void onEvent(CustomEvent event) {
        Notification notification = NotificationFactory.createNotification(event, msgProvider);
        // TODO: 14.02.2025 usunąć tego souta
        System.out.printf("Wysyłam powiadomienie (%s)%n", notification.getClass().getSimpleName());
        sender.send(notification);
        notificationRepository.save(notification);
    }
}