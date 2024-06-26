package com.example.libraryapp.domain.notification.strategies;

import com.example.libraryapp.domain.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
public class SystemNotificationStrategy implements NotificationStrategy {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void send(NotificationDto notification) {
        String memberId = String.valueOf(notification.getMemberId());
        messagingTemplate.convertAndSendToUser(memberId, "/queue/notifications", notification);
    }
}