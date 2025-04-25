package com.example.loanservice.core;

import com.example.loanservice.domain.Constants;
import com.example.loanservice.domain.model.*;
import com.example.loanservice.domain.ports.BookItemRequestServicePort;
import com.example.loanservice.domain.ports.EventPublisherPort;
import com.example.loanservice.domain.ports.FineServicePort;
import com.example.loanservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class RenewBookItemLoanUseCase {
    private final UserServicePort userService;
//    private final AuthenticationFacade authFacade;
    private final BookItemRequestServicePort bookItemRequestService;
    private final BookItemLoanService bookItemLoanService;
    private final FineServicePort fineService;
    private final EventPublisherPort publisher;

    BookItemLoan execute(BookItemId bookItemId, UserId userId) {
//        authFacade.validateOwnerOrAdminAccess(userId);
        userService.verifyUserForBookItemRenewal(userId);
        fineService.verifyUserForFines(userId);
        bookItemRequestService.ensureBookItemNotRequested(bookItemId);
        BookItemLoan loanToUpdate = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoanService.validateBookItemLoanForRenewal(loanToUpdate);
        loanToUpdate.setDueDate(new LoanDueDate(LocalDateTime.now().plusDays(Constants.MAX_LENDING_DAYS)));
        BookItemLoan savedLoan = bookItemLoanService.save(loanToUpdate);
        publisher.publishLoanProlongedEvent(BookItemLoanMapper.toDto(savedLoan));
        return savedLoan;
    }
}
