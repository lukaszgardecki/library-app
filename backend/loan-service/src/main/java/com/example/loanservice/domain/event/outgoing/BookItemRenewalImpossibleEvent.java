package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRenewalImpossibleEvent {
    private final BookItemId bookItemId;
    private final UserId userId;

    public BookItemRenewalImpossibleEvent(BookItemId bookItemId, UserId userId) {
        this.bookItemId = bookItemId;
        this.userId = userId;
    }
}
