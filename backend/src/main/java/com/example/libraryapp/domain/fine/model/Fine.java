package com.example.libraryapp.domain.fine.model;

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
    private Long id;
    private Long userId;
    private Long loanId;
    private BigDecimal amount;
    private String description;
    private FineStatus status;

    public boolean isPaid() {
        return status == FineStatus.PAID;
    }
}
