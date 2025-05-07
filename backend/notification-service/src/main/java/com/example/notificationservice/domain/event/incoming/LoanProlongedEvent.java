package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.integration.catalog.BookItemId;
import com.example.notificationservice.domain.integration.catalog.Title;
import com.example.notificationservice.domain.integration.loan.LoanDueDate;
import com.example.notificationservice.domain.integration.loan.LoanId;
import com.example.notificationservice.domain.integration.loan.LoanReturnDate;
import com.example.notificationservice.domain.model.values.UserId;
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
