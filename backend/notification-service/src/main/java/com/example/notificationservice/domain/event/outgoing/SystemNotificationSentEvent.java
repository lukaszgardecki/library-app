package com.example.notificationservice.domain.event.outgoing;

import com.example.notificationservice.domain.model.NotificationSubject;
import com.example.notificationservice.domain.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemNotificationSentEvent {
    private final UserId userId;
    private final NotificationSubject notificationSubject;
}



