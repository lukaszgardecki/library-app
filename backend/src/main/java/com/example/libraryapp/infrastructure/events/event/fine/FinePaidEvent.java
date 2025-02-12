package com.example.libraryapp.infrastructure.events.event.fine;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FinePaidEvent extends FineEvent {

    public FinePaidEvent(Long userId, BigDecimal fineAmount) {
        super(userId, fineAmount);
    }
}
