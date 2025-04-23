package com.example.warehouseservice.domain.event;

import com.example.warehouseservice.domain.model.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
