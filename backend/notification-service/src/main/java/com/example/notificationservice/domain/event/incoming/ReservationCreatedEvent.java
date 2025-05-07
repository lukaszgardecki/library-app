package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.integration.catalog.BookItemId;
import com.example.notificationservice.domain.integration.loan.LoanDueDate;
import com.example.notificationservice.domain.integration.catalog.Title;
import com.example.notificationservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent {
    private BookItemId bookItemId;
    private UserId userId;
    private int queue;
    private LoanDueDate loanDueDate;
    private Title bookTitle;
}
