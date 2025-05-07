package com.example.loanservice.domain.event.incoming;

import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanDueDate;
import com.example.loanservice.domain.integration.catalog.Title;
import com.example.loanservice.domain.model.values.UserId;
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
