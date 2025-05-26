package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanStatus;
import com.example.loanservice.domain.ports.in.EventListenerPort;
import com.example.loanservice.domain.ports.out.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BookItemLoanService bookItemLoanService;
    private final EventPublisherPort publisher;

    @Override
    public void handleBookItemReservedEvent(BookItemId bookItemId) {
        BookItemLoan currentLoan = bookItemLoanService.getBookItemLoan(bookItemId, LoanStatus.CURRENT);
        publisher.publishLoanProlongationNotAllowedEvent(currentLoan.getBookItemId(), currentLoan.getUserId());
    }
}
