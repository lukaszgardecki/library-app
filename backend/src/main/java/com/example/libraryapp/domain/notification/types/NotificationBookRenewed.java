package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RENEWED")
public class NotificationBookRenewed extends Notification {
    public NotificationBookRenewed(LendingDto lending) {
        super(lending.getMember().getId());
        this.subject = Message.NOTIFICATION_BOOK_RENEWED_SUBJECT.getMessage();
        this.content = Message.NOTIFICATION_BOOK_RENEWED_CONTENT.getMessage();
        this.bookId = lending.getBookItem().getBook().getId();
        this.bookTitle = lending.getBookItem().getBook().getTitle();
    }
}
