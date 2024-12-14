package com.example.libraryapp.NEWdomain.useractivity.types.notify;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import lombok.Getter;

@Getter
public abstract class NotificationSentActivity extends UserActivity {
    private String notificationSubject;

    public NotificationSentActivity(Long userId, String notificationSubject) {
        super(userId);
        this.notificationSubject = notificationSubject;
    }
}
