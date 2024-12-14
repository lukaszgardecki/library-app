package com.example.libraryapp.NEWinfrastructure.events.event.user;

import lombok.Getter;

@Getter
public class UserRegisteredEvent extends UserEvent {
    private String msg;

    public UserRegisteredEvent(Long userId, String msg) {
        super(userId);
        this.msg = msg;
    }
}