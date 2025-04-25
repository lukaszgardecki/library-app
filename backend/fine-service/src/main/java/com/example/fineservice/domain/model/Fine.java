package com.example.fineservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Fine {
    private FineId id;
    private UserId userId;
    private LoanId loanId;
    private FineAmount amount;
    private FineDescription description;
    private FineStatus status;

    public boolean isPaid() {
        return status == FineStatus.PAID;
    }
}
