package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitem.Price;
import com.example.userservice.domain.model.bookitemloan.LoanDueDate;
import com.example.userservice.domain.model.bookitemloan.LoanId;
import com.example.userservice.domain.model.bookitemloan.LoanReturnDate;
import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookItemLostEvent {
    private LoanId loanId;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private Price charge;
}
