package com.example.libraryapp.domain.notification.strategies;

import com.example.libraryapp.domain.notification.dto.NotificationDto;

public interface NotificationStrategy {
    void send(NotificationDto notification);
}
