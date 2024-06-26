package com.example.libraryapp.domain.notification.strategies;

import com.example.libraryapp.domain.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SmsNotificationStrategy implements NotificationStrategy {
    private final String phoneNumber;

    @Override
    public void send(NotificationDto notification) {
        // TODO: 14.06.2024 implement SMS notification sending
    }
}