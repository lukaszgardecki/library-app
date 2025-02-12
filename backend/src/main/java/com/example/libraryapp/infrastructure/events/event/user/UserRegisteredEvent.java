package com.example.libraryapp.infrastructure.events.event.user;

import lombok.Getter;

@Getter
public class UserRegisteredEvent extends UserEvent {
    private String msg;

    public UserRegisteredEvent(Long userId, String userFirstName, String userLastName, String msg) {
        super(userId, userFirstName, userLastName);
        this.msg = msg;
    }
}