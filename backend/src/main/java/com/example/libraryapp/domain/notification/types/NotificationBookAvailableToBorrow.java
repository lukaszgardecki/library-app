package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_AVAILABLE_TO_BORROW")
public class NotificationBookAvailableToBorrow extends Notification {
    public NotificationBookAvailableToBorrow(ReservationResponse reservation) {
        super(reservation.getMember().getId());
        this.subject = Message.REASON_BOOK_AVAILABLE_TO_BORROW;
        this.content = Message.BOOK_AVAILABLE_TO_BORROW.formatted(reservation.getBookItem().getBook().getTitle());
        this.bookId = reservation.getBookItem().getId();
        this.bookTitle = reservation.getBookItem().getBook().getTitle();
    }
}
