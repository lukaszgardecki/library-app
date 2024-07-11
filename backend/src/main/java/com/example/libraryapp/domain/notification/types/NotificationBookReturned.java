package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RETURNED")
public class NotificationBookReturned extends Notification {
    public NotificationBookReturned(LendingDto lending) {
        super(lending.getMember().getId());
        this.subject = Message.NOTIFICATION_BOOK_RETURNED_SUBJECT.getMessage();
        this.content = Message.NOTIFICATION_BOOK_RETURNED_CONTENT.getMessage();
        this.bookId = lending.getBookItem().getBook().getId();
        this.bookTitle = lending.getBookItem().getBook().getTitle();
    }
}
