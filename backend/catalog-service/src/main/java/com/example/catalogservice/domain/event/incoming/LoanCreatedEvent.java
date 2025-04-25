package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.LoanId;
import com.example.catalogservice.domain.model.LoanReturnDate;
import com.example.catalogservice.domain.model.RequestId;
import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.bookitem.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanCreatedEvent {
    private LoanId loanId;
    private LoanCreationDate loanCreationDate;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private RequestId requestId;
}

