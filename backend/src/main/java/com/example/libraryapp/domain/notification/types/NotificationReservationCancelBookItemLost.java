package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("RESERVATION_CANCEL_BOOK_ITEM_LOST")
public class NotificationReservationCancelBookItemLost extends Notification {
    public NotificationReservationCancelBookItemLost(ReservationResponse reservation) {
        super(reservation.getMember().getId());
        this.subject = Message.NOTIFICATION_BOOK_LOST_SUBJECT.getMessage();
        this.content = Message.RESERVATION_CANCELLATION_BOOK_ITEM_LOST.getMessage();
        this.bookId = reservation.getBookItem().getBook().getId();
        this.bookTitle = reservation.getBookItem().getBook().getTitle();
    }
}
