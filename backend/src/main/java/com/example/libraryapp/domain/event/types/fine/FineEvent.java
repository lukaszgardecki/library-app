package com.example.libraryapp.domain.event.types.fine;

import com.example.libraryapp.domain.event.types.CustomEvent;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
abstract class FineEvent extends CustomEvent {
    private final BigDecimal fineAmount;

    protected FineEvent(Long userId, BigDecimal fineAmount) {
        super(userId);
        this.fineAmount = fineAmount;
    }
}
