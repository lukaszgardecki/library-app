package com.example.libraryapp.domain.useractivity.types.user;

import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.user.UserEvent;

public class LoginSuccessActivity extends UserActivity {

    public LoginSuccessActivity(UserEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.LOGIN;
        this.message = "Message.ACTION_LOGIN_SUCCEEDED.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
    }

//    public LoginSuccessActivity(Long userId, String userFirstName, String userLastName, String userCardBarcode) {
//        super(userId);
//        this.type = UserActivityType.LOGIN;
//        this.message = "Message.ACTION_LOGIN_SUCCEEDED.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
//    }
}
