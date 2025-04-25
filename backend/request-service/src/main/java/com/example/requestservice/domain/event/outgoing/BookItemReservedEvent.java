package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.LoanDueDate;
import com.example.requestservice.domain.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookItemReservedEvent {
    private final BookItemId bookItemId;
    private final UserId userId;
    private final int queue;
    private final LoanDueDate dueDate;
}
