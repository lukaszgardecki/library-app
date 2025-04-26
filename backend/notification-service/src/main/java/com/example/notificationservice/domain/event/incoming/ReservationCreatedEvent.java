package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.model.BookItemId;
import com.example.notificationservice.domain.model.LoanDueDate;
import com.example.notificationservice.domain.model.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationCreatedEvent {
    private BookItemId bookItemId;
    private UserId userId;
    private int queue;
    private LoanDueDate loanDueDate;
}
