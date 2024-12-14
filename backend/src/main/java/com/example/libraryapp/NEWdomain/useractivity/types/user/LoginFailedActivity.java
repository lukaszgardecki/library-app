package com.example.libraryapp.NEWdomain.useractivity.types.user;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserEvent;

public class LoginFailedActivity extends UserActivity {

    public LoginFailedActivity(UserEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.LOGIN_FAILED;
        this.message = "Message.ACTION_LOGIN_FAILED.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
    }

//    public LoginFailedActivity(Long userId, String userFirstName, String userLastName, String userCardBarcode) {
//        super(userId);
//        this.type = UserActivityType.LOGIN_FAILED;
//        this.message = "Message.ACTION_LOGIN_FAILED.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
//    }
}
