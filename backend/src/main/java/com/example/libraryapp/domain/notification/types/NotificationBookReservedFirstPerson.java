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
        this.subject = Message.REASON_BOOK_RESERVED;
        this.content = Message.BOOK_RESERVED_FIRST_PERSON.formatted(
                currentLending.getBookItem().getBook().getTitle(),
                currentLending.getDueDate()
        );
        this.bookId = currentLending.getBookItem().getId();
        this.bookTitle = currentLending.getBookItem().getBook().getTitle();
    }
}