package com.example.userservice.domain.integration.loan;

import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.loan.values.*;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemLoan {
    private LoanId id;
    private LoanCreationDate creationDate;
    private LoanDueDate dueDate;
    private LoanReturnDate returnDate;
    private LoanStatus status;
    private UserId userId;
    private BookItemId bookItemId;
}
