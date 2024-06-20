package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.notification.dto.NotificationDto;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("NOTIFICATION_SMS")
public class NotificationSentSmsAction extends Action {
    public NotificationSentSmsAction(NotificationDto notification) {
        super(notification.getMemberId());
        this.message = Message.ACTION_NOTIFICATION_SENT_SMS.formatted(notification.getSubject());
    }
}
