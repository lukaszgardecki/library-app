package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.catalog.Title;
import com.example.activityservice.domain.integration.catalog.BookItemId;
import com.example.activityservice.domain.integration.loan.LoanDueDate;
import com.example.activityservice.domain.integration.loan.LoanId;
import com.example.activityservice.domain.integration.loan.LoanReturnDate;
import com.example.activityservice.domain.model.values.UserId;
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
