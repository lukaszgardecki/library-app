package com.example.libraryapp.domain.notification.strategies;

import com.example.libraryapp.domain.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy {
    private final String email;

    @Override
    public void send(NotificationDto notification) {
        // TODO: 14.06.2024 implement Email notification sending
    }
}