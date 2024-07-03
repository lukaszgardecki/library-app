package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("REQUEST_CREATED")
public class NotificationRequestCreated extends Notification {
    public NotificationRequestCreated(ReservationResponse reservation) {
        super(reservation.getMember().getId());
        this.subject = Message.REASON_REQUEST_CREATED;
        this.content = Message.RESERVATION_CREATED;
        this.bookId = reservation.getBookItem().getId();
        this.bookTitle = reservation.getBookItem().getBook().getTitle();
    }
}