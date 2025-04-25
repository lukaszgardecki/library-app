package com.example.fineservice.core;

import com.example.fineservice.domain.model.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ProcessBookItemLostUseCase {
    private final FineService fineService;

    void execute(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate, Price bookItemPrice) {
        fineService.processFineForBookReturn(returnDate, dueDate, userId, loanId);
        fineService.processFineForBookLost(userId, loanId, bookItemPrice);
    }
}
