package com.example.notificationservice.domain.event.outgoing;

import com.example.notificationservice.domain.model.values.NotificationSubject;
import com.example.notificationservice.domain.model.values.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailNotificationSentEvent {
    private final UserId userId;
    private final NotificationSubject notificationSubject;
}


