package com.example.fineservice.domain.event.outgoing;

import com.example.fineservice.domain.event.CustomEvent;
import com.example.fineservice.domain.model.FineAmount;
import com.example.fineservice.domain.model.UserId;
import lombok.Getter;

@Getter
public class FinePaidEvent extends CustomEvent {
    private final FineAmount fineAmount;

    public FinePaidEvent(UserId userId, FineAmount fineAmount) {
        super(userId);
        this.fineAmount = fineAmount;
    }
}
