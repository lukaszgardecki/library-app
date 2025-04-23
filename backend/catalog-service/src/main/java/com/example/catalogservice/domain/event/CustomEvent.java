package com.example.catalogservice.domain.event;

import com.example.catalogservice.domain.model.bookitem.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
