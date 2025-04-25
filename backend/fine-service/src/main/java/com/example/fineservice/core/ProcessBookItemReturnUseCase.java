package com.example.fineservice.core;

import com.example.fineservice.domain.model.LoanDueDate;
import com.example.fineservice.domain.model.LoanId;
import com.example.fineservice.domain.model.LoanReturnDate;
import com.example.fineservice.domain.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessBookItemReturnUseCase {
    private final FineService fineService;

    void execute(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate) {
        fineService.processFineForBookReturn(returnDate, dueDate, userId, loanId);
    }
}
