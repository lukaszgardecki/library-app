package com.example.libraryapp.infrastructure.events.event.user;

import lombok.Getter;

@Getter
public class UserAuthFailedEvent extends UserEvent {
    private String msg;

    public UserAuthFailedEvent(Long userId, String userFirstName, String userLastName, String msg) {
        super(userId, userFirstName, userLastName);
        this.msg = msg;
    }
}



