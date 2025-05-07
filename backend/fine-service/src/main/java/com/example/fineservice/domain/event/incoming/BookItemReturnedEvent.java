package com.example.fineservice.domain.event.incoming;

import com.example.fineservice.domain.integration.catalog.BookItemId;
import com.example.fineservice.domain.integration.loan.LoanDueDate;
import com.example.fineservice.domain.integration.loan.LoanReturnDate;
import com.example.fineservice.domain.integration.catalog.Title;
import com.example.fineservice.domain.model.values.LoanId;
import com.example.fineservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemReturnedEvent {
    private LoanId loanId;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private Title bookTitle;
}
