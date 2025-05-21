package com.example.loanservice.core;

import com.example.loanservice.domain.constants.Constants;
import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanDueDate;
import com.example.loanservice.domain.model.values.LoanStatus;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.BookItemRequestServicePort;
import com.example.loanservice.domain.ports.out.EventPublisherPort;
import com.example.loanservice.domain.ports.out.FineServicePort;
import com.example.loanservice.domain.ports.out.UserServicePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
class RenewBookItemLoanUseCase {
    private final UserServicePort userService;
    private final BookItemRequestServicePort bookItemRequestService;
    private final BookItemLoanService bookItemLoanService;
    private final FineServicePort fineService;
    private final EventPublisherPort publisher;

    BookItemLoan execute(BookItemId bookItemId, UserId userId) {
        userService.verifyUserForBookItemRenewal(userId);
        fineService.verifyUserForFines(userId);
        bookItemRequestService.ensureBookItemNotRequested(bookItemId);
        BookItemLoan loanToUpdate = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoanService.validateBookItemLoanForRenewal(loanToUpdate);
        loanToUpdate.setDueDate(new LoanDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS)));
        BookItemLoan savedLoan = bookItemLoanService.save(loanToUpdate);
        publisher.publishLoanProlongedEvent(savedLoan);
        return savedLoan;
    }
}
