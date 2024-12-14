package com.example.libraryapp.NEWinfrastructure.events.event.user;

import lombok.Getter;

@Getter
public class UserAuthFailedEvent extends UserEvent {
    private String msg;

    public UserAuthFailedEvent(Long userId, String msg) {
        super(userId);
        this.msg = msg;
    }
}



