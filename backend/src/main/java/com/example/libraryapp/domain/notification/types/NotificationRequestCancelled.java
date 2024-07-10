package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REQUEST_CANCELLED")
public class NotificationRequestCancelled extends Notification {
    public NotificationRequestCancelled(ReservationResponse reservation) {
        super(reservation.getMember().getId());
        this.subject = Message.NOTIFICATION_REQUEST_CANCELLED_SUBJECT.getMessage();
        this.content = Message.NOTIFICATION_REQUEST_CANCELLED_CONTENT.getMessage();
        this.bookId = reservation.getBookItem().getBook().getId();
        this.bookTitle = reservation.getBookItem().getBook().getTitle();
    }
}
