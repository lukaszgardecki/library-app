package com.example.libraryapp.infrastructure.events.event.fine;

import com.example.libraryapp.infrastructure.events.event.CustomEvent;
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
