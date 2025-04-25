package com.example.fineservice.domain.event;

import com.example.fineservice.domain.model.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
