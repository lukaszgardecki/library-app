package com.example.requestservice.domain.event.incoming;

import com.example.requestservice.domain.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookItemLostEvent {
    private LoanId id;
    private LoanDueDate dueDate;
    private LoanReturnDate returnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private Price charge;
}
