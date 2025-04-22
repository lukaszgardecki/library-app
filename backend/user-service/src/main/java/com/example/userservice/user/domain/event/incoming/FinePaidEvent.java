package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.model.fine.FineAmount;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class FinePaidEvent extends FineEvent {

    public FinePaidEvent(UserId userId, FineAmount fineAmount) {
        super(userId, fineAmount);
    }
}
