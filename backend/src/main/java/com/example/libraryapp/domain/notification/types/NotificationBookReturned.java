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
        this.subject = Message.REASON_BOOK_RETURNED;
        this.content = Message.BOOK_RETURNED;
        this.bookId = lending.getBookItem().getId();
        this.bookTitle = lending.getBookItem().getBook().getTitle();
    }
}
