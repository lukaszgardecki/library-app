package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.notification.NotificationDetails;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("NOTIFICATION_SMS")
public class NotificationSentSmsAction extends Action {
    public NotificationSentSmsAction(NotificationDetails details) {
        super(details.getMemberId());
        this.message = Message.ACTION_NOTIFICATION_SENT_SMS.formatted(
            details.getReason(),
            details.getBookBarcode()
        );
    }
}
