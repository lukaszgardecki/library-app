package com.example.libraryapp.domain.useractivity.types.notify;

import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import lombok.Getter;

@Getter
public abstract class NotificationSentActivity extends UserActivity {
    private NotificationSubject notificationSubject;

    public NotificationSentActivity(UserId userId, NotificationSubject notificationSubject) {
        super(userId);
        this.notificationSubject = notificationSubject;
    }
}
