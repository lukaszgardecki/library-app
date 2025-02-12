package com.example.libraryapp.domain.notification.types.user;


import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;

public class UserRegisteredNotification extends Notification {

    public UserRegisteredNotification(UserRegisteredEvent event) {
        super(event.getUserId());
        this.type = NotificationType.USER_REGISTERED;
        this.subject = "Rejestrowanie użytkownika";
        this.content = "Cośtamw witaj pomyślnie założyłeś konto w naszej bibliotece";
    }

//    public UserRegisteredNotification(Long userId) {
//        super(userId);
//        this.type = NotificationType.USER_REGISTERED;
//        this.subject = "Rejestrowanie użytkownika";
//        this.content = "Cośtamw witaj pomyślnie założyłeś konto w naszej bibliotece";
//    }
}
