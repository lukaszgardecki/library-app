package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.event.CustomEvent;
import com.example.userservice.user.domain.model.fine.FineAmount;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
abstract class FineEvent extends CustomEvent {
    private final FineAmount fineAmount;

    protected FineEvent(UserId userId, FineAmount fineAmount) {
        super(userId);
        this.fineAmount = fineAmount;
    }
}
