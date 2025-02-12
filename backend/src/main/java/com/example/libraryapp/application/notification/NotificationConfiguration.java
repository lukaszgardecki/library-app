package com.example.libraryapp.application.notification;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.notification.ports.*;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
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
            NotificationPreferencesRepositoryPort notificationPreferencesPort,
            EventPublisherPort publisherPort
    ) {
        return new NotificationEventListenerImpl(
                new NotificationSender(
                        smsNotificationPort,
                        emailNotificationPort,
                        systemNotificationPort,
                        notificationPreferencesPort,
                        publisherPort
                )
        );
    }
}
