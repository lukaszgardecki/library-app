package com.example.authservice.auth.domain.event;

import com.example.authservice.auth.domain.model.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
