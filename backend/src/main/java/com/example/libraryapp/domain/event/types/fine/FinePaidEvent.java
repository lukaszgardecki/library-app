package com.example.libraryapp.domain.event.types.fine;

import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FinePaidEvent extends FineEvent {

    public FinePaidEvent(UserId userId, FineAmount fineAmount) {
        super(userId, fineAmount);
    }
}
