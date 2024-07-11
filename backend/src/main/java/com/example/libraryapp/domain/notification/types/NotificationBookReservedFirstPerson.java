package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RESERVED_FIRST_PERSON")
public class NotificationBookReservedFirstPerson extends Notification {
    public NotificationBookReservedFirstPerson(Long userId, LendingDto currentLending) {
        super(userId);
        this.subject = Message.NOTIFICATION_BOOK_RESERVED_SUBJECT.getMessage();
        this.content = Message.NOTIFICATION_BOOK_RESERVED_CONTENT_FIRST_PERSON.getMessage(
                currentLending.getBookItem().getBook().getTitle(),
                currentLending.getDueDate()
        );
        this.bookId = currentLending.getBookItem().getBook().getId();
        this.bookTitle = currentLending.getBookItem().getBook().getTitle();
    }
}
