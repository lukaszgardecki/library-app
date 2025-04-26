package com.example.notificationservice.infrastructure.notification;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.ports.SystemNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SystemNotificationSenderAdapter implements SystemNotificationPort {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void send(Notification notification) {
        System.out.println("Wysyłam powiadomienie systemowe: " + notification.getContent().value());
        String userId = String.valueOf(notification.getUserId().value());
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", notification);
    }
}
