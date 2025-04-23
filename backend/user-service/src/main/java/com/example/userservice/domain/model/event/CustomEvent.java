package com.example.userservice.domain.model.event;

import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
