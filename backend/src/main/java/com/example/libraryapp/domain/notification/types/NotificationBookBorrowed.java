package com.example.libraryapp.domain.notification.types;

import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.notification.Notification;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_BORROWED")
public class NotificationBookBorrowed extends Notification {
    public NotificationBookBorrowed(LendingDto lending) {
        super(lending.getMember().getId());
        this.subject = Message.REASON_BOOK_BORROWED;
        this.content = Message.BOOK_BORROWED;
        this.bookId = lending.getBookItem().getBook().getId();
        this.bookTitle = lending.getBookItem().getBook().getTitle();
    }
}
