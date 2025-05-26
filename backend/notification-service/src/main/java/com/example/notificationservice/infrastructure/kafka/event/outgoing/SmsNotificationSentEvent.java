package com.example.notificationservice.infrastructure.kafka.event.outgoing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SmsNotificationSentEvent {
    private final Long userId;
    private final String notificationSubject;
}


