package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.notification.NotificationSubject;
import com.example.activityservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemNotificationSentEvent {
    private UserId userId;
    private NotificationSubject notificationSubject;
}



