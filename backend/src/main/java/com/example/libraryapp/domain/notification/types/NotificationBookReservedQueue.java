package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RESERVED_QUEUE")
public class NotificationBookReservedQueue extends Notification {
    public NotificationBookReservedQueue(ReservationResponse reservation, int queuePosition) {
        super(reservation.getMember().getId());
        this.subject = Message.REASON_BOOK_RESERVED;
        this.content = Message.BOOK_RESERVED_QUEUE.formatted(
                reservation.getBookItem().getBook().getTitle(),
                queuePosition - 1
        );
        this.bookId = reservation.getBookItem().getId();
        this.bookTitle = reservation.getBookItem().getBook().getTitle();
    }
}
