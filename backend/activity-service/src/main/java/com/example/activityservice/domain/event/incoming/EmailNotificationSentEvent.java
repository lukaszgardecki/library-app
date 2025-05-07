package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.model.NotificationSubject;
import com.example.activityservice.domain.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationSentEvent {
    private UserId userId;
    private NotificationSubject notificationSubject;
}


