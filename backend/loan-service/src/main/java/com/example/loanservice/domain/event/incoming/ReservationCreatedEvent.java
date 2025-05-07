package com.example.loanservice.domain.event.incoming;

import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.LoanDueDate;
import com.example.loanservice.domain.model.Title;
import com.example.loanservice.domain.model.UserId;
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
