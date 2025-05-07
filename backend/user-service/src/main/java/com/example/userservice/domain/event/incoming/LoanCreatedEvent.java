package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.RequestId;
import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitemloan.LoanCreationDate;
import com.example.userservice.domain.model.bookitemloan.LoanDueDate;
import com.example.userservice.domain.model.bookitemloan.LoanId;
import com.example.userservice.domain.model.bookitemloan.LoanReturnDate;
import com.example.userservice.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreatedEvent {
    private LoanId loanId;
    private LoanCreationDate loanCreationDate;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private RequestId requestId;
    private Boolean isReferenceOnly;
    private Title bookTitle;
}

