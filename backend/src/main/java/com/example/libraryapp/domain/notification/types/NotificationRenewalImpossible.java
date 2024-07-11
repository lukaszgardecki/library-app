package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("RENEWAL_IMPOSSIBLE")
public class NotificationRenewalImpossible extends Notification {
    public NotificationRenewalImpossible(LendingDto lending) {
        super(lending.getMember().getId());
        this.subject = Message.NOTIFICATION_RENEWAL_IMPOSSIBLE_SUBJECT.getMessage();
        this.content = Message.NOTIFICATION_RENEWAL_IMPOSSIBLE_CONTENT.getMessage(lending.getBookItem().getBook().getTitle());
        this.bookId = lending.getBookItem().getBook().getId();
        this.bookTitle = lending.getBookItem().getBook().getTitle();
    }
}
