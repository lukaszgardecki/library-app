package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.model.fine.FineAmount;
import lombok.Getter;

@Getter
public class FinePaidEvent extends FineEvent {

    public FinePaidEvent(UserId userId, FineAmount fineAmount) {
        super(userId, fineAmount);
    }
}
