package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.integration.catalog.Title;
import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.loan.LoanDueDate;
import com.example.userservice.domain.integration.loan.LoanId;
import com.example.userservice.domain.integration.loan.LoanReturnDate;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanProlongedEvent {
    private LoanId loanId;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private Title bookTitle;
}
