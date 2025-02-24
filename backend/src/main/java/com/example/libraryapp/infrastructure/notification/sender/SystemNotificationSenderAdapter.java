package com.example.libraryapp.infrastructure.notification.sender;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.SystemNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SystemNotificationSenderAdapter implements SystemNotificationPort {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam powiadomienie systemowe: " + notification.getContent());
        String userId = String.valueOf(notification.getUserId());
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", notification);
    }
}
