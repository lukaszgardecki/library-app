package com.example.libraryapp.infrastructure.events.event;

import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected Long userId;

    protected CustomEvent(Long userId) {
        this.userId = userId;
    }
}
