package com.example.libraryapp.domain.event.types;

import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public abstract class CustomEvent {
    protected UserId userId;

    protected CustomEvent(UserId userId) {
        this.userId = userId;
    }
}
