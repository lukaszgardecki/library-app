package com.example.loanservice.domain.model;

import com.example.loanservice.domain.model.values.*;
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
