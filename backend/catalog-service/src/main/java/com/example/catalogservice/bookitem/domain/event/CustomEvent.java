package com.example.catalogservice.bookitem.domain.event;

import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
