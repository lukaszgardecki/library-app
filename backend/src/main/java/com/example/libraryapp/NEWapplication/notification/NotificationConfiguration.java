package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWdomain.notification.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

    @Bean
    NotificationFacade notificationFacade(
            AuthenticationFacade authenticationFacade,
            NotificationRepositoryPort notificationRepositoryPort
    ) {
        NotificationOwnershipService notificationOwnershipService = new NotificationOwnershipService(authenticationFacade);
        return new NotificationFacade(
                new GetPageOfNotificationsByUserIdUseCase(notificationOwnershipService, notificationRepositoryPort),
                new GetNotificationUseCase(notificationOwnershipService, notificationRepositoryPort),
                new MarkAsReadUseCase(notificationOwnershipService, notificationRepositoryPort),
                new DeleteNotificationUseCase(notificationOwnershipService, notificationRepositoryPort)
        );
    }


    @Bean
    NotificationEventListenerImpl notificationEventListener(
            SmsNotificationPort smsNotificationPort,
            EmailNotificationPort emailNotificationPort,
            SystemNotificationPort systemNotificationPort,
            NotificationPreferencesRepositoryPort notificationPreferencesPort
    ) {
        return new NotificationEventListenerImpl(
                new NotificationSender(
                        smsNotificationPort,emailNotificationPort, systemNotificationPort, notificationPreferencesPort
                )
        );
    }
}
