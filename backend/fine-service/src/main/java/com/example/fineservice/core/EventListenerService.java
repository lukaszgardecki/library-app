package com.example.fineservice.core;

import com.example.fineservice.domain.model.*;
import com.example.fineservice.domain.ports.EventListenerPort;
import com.example.fineservice.domain.ports.FineRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final FineRepositoryPort fineRepository;

    @Override
    public void handleBookItemReturnedEvent(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate) {
        BigDecimal amount = FineCalculator.calculateFine(returnDate.value(), dueDate.value());
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            Fine fineToSave = Fine.builder()
                    .amount(new FineAmount(amount))
                    .userId(userId)
                    .loanId(loanId)
                    .status(FineStatus.PENDING)
                    .build();
            fineRepository.save(fineToSave);
        }
    }

    @Override
    public void handleBookItemLostEvent(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate, Price charge) {
        handleBookItemReturnedEvent(loanId, userId, returnDate, dueDate);
        Fine fineToSave = Fine.builder()
                .amount(new FineAmount(charge.value()))
                .userId(userId)
                .loanId(loanId)
                .status(FineStatus.PENDING)
                .build();
        fineRepository.save(fineToSave);
    }
}
