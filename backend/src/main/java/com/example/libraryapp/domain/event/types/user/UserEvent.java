package com.example.libraryapp.domain.event.types.user;

import com.example.libraryapp.domain.event.types.CustomEvent;
import lombok.Getter;

@Getter
public abstract class UserEvent extends CustomEvent {
    private final String userFirstName;
    private final String userLastName;

    protected UserEvent(Long userId, String userFirstName, String userLastName) {
        super(userId);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }
}
