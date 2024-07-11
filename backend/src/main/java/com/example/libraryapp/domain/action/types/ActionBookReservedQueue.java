package com.example.libraryapp.domain.action.types;

import com.example.libraryapp.domain.action.Action;
import com.example.libraryapp.management.Message;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("BOOK_RESERVED_QUEUE")
public class ActionBookReservedQueue extends Action {
    public ActionBookReservedQueue(Long userId, String bookTitle, int queuePosition) {
        super(userId);
        this.message = Message.ACTION_BOOK_RESERVED_QUEUE
                .getMessage(bookTitle, queuePosition);
    }
}
