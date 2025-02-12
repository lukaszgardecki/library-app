package com.example.libraryapp.infrastructure.events.event.user;

import com.example.libraryapp.infrastructure.events.event.CustomEvent;
import lombok.Getter;

@Getter
public abstract class UserEvent extends CustomEvent {
    private String userFirstName;
    private String userLastName;

    protected UserEvent(Long userId, String userFirstName, String userLastName) {
        super(userId);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }
}
