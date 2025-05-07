package com.example.fineservice.domain.event.incoming;

import com.example.fineservice.domain.integration.catalog.BookId;
import com.example.fineservice.domain.integration.catalog.BookItemId;
import com.example.fineservice.domain.integration.catalog.Price;
import com.example.fineservice.domain.integration.catalog.Title;
import com.example.fineservice.domain.integration.loan.*;
import com.example.fineservice.domain.model.values.LoanId;
import com.example.fineservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemLostEvent {
    private LoanId loanId;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private BookId bookId;
    private Price charge;
    private Title bookTitle;
}
