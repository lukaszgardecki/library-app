package com.example.libraryapp.domain.event.types.user;

import lombok.Getter;

@Getter
public class UserAuthSuccessEvent extends UserEvent {
    private String msg;

    public UserAuthSuccessEvent(Long userId, String userFirstName, String userLastName, String msg) {
        super(userId, userFirstName, userLastName);
        this.msg = msg;
    }
}
