package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_LOST")
public class NotificationBookLost extends Notification {
    public NotificationBookLost(LendingDto lending) {
        super(lending.getMember().getId());
        this.subject = Message.NOTIFICATION_BOOK_LOST_SUBJECT.getMessage();
        this.content = Message.NOTIFICATION_BOOK_LOST_CONTENT.getMessage(lending.getBookItem().getPrice());
        this.bookId = lending.getBookItem().getBook().getId();
        this.bookTitle = lending.getBookItem().getBook().getTitle();
    }
}
