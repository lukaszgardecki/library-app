package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.catalog.BookItemId;
import com.example.activityservice.domain.integration.loan.LoanDueDate;
import com.example.activityservice.domain.integration.catalog.Title;
import com.example.activityservice.domain.model.values.UserId;
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
