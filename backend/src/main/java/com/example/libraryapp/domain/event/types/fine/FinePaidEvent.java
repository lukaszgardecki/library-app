package com.example.libraryapp.domain.event.types.fine;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FinePaidEvent extends FineEvent {

    public FinePaidEvent(Long userId, BigDecimal fineAmount) {
        super(userId, fineAmount);
    }
}
