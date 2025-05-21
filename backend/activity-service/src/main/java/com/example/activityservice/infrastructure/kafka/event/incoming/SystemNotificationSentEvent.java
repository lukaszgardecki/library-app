package com.example.activityservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemNotificationSentEvent {
    private Long userId;
    private String notificationSubject;
}



