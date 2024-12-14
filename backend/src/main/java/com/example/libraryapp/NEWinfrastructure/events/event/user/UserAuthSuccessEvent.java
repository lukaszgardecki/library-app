package com.example.libraryapp.NEWinfrastructure.events.event.user;

import lombok.Getter;

@Getter
public class UserAuthSuccessEvent extends UserEvent {
    private String msg;

    public UserAuthSuccessEvent(Long userId, String msg) {
        super(userId);
        this.msg = msg;
    }
}
