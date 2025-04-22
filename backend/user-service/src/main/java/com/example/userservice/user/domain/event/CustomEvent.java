package com.example.userservice.user.domain.event;

import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
