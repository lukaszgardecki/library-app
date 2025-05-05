package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.LoanDueDate;
import com.example.requestservice.domain.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent {
    private BookItemId bookItemId;
    private UserId userId;
    private int queue;
    private LoanDueDate loanDueDate;
}
