package com.example.libraryapp.domain.useractivity.types.user;

import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.infrastructure.events.event.user.UserEvent;

public class RegisterActivity extends UserActivity {

    public RegisterActivity(UserEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.REGISTER;
        this.message = "Message.ACTION_REGISTER.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
    }

//    public RegisterActivity(Long userId, String userFirstName, String userLastName, String userCardBarcode) {
//        super(userId);
//        this.type = UserActivityType.REGISTER;
//        this.message = "Message.ACTION_REGISTER.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
//    }
}
