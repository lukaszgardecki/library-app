package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.domain.notification.NotificationDetails;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("NOTIFICATION_EMAIL")
public class NotificationSentEmailAction extends Action {
    public NotificationSentEmailAction(NotificationDetails details) {
        super(details.getMemberId());
        this.message = Message.ACTION_NOTIFICATION_SENT_EMAIL.formatted(
            details.getReason(),
            details.getBookBarcode()
        );
    }
}
