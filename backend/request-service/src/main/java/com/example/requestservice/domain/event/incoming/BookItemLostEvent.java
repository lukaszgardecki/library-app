package com.example.requestservice.domain.event.incoming;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.integration.catalog.Price;
import com.example.requestservice.domain.integration.catalog.Title;
import com.example.requestservice.domain.integration.loan.LoanDueDate;
import com.example.requestservice.domain.integration.loan.LoanId;
import com.example.requestservice.domain.integration.loan.LoanReturnDate;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.UserId;
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
