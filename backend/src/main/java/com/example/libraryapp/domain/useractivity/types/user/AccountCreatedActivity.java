package com.example.libraryapp.domain.useractivity.types.user;

import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.user.UserEvent;

public class AccountCreatedActivity extends UserActivity {

    public AccountCreatedActivity(UserEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.LOGIN_FAILED;
        this.message = "Message.ACTION_LOGIN_FAILED.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
    }

//    public AccountCreatedActivity(Long userId, String userFirstName, String userLastName, String userCardBarcode) {
//        super(userId);
//        this.type = UserActivityType.LOGIN_FAILED;
//        this.message = "Message.ACTION_LOGIN_FAILED.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
//    }
}
