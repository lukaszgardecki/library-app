package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.LoanStatus;
import com.example.loanservice.domain.ports.EventListenerPort;
import com.example.loanservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BookItemLoanService bookItemLoanService;
    private final EventPublisherPort publisher;

    @Override
    public void handleBookItemReservedEvent(BookItemId bookItemId) {
        BookItemLoan currentLoan = bookItemLoanService.getBookItemLoan(bookItemId, LoanStatus.CURRENT);
        publisher.publishBookItemRenewalImpossibleEvent(currentLoan.getBookItemId(), currentLoan.getUserId());
    }
}
