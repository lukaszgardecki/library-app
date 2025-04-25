package com.example.loanservice.core;

import com.example.loanservice.domain.model.*;
import com.example.loanservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ReturnBookItemUseCase {
//    private final AuthenticationFacade authFacade;
    private final BookItemLoanService bookItemLoanService;
    private final EventPublisherPort publisher;

    void execute(BookItemId bookItemId, UserId userId) {
//        authFacade.validateOwnerOrAdminAccess(userId);
        BookItemLoan bookItemLoan = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoan.setReturnDate(new LoanReturnDate(LocalDateTime.now()));
        bookItemLoan.setStatus(LoanStatus.COMPLETED);
        bookItemLoanService.save(bookItemLoan);
        publisher.publishBookItemReturnedEvent(BookItemLoanMapper.toDto(bookItemLoan));
    }
}
