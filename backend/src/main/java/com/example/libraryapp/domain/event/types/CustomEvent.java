package com.example.libraryapp.domain.event.types;

import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected Long userId;

    protected CustomEvent(Long userId) {
        this.userId = userId;
    }
}
