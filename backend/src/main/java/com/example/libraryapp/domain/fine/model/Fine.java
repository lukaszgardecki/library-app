package com.example.libraryapp.domain.fine.model;

import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
