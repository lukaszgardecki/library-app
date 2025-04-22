package com.example.libraryapp.core.notification;

import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
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
        NotificationService notificationService = new NotificationService(notificationRepositoryPort);
        NotificationAccessControlService notificationAccessControlService =
                new NotificationAccessControlService(notificationService, authenticationFacade);
        return new NotificationFacade(
                new GetPageOfNotificationsByUserIdUseCase(notificationAccessControlService, notificationRepositoryPort),
                new GetNotificationUseCase(notificationAccessControlService, notificationService),
                new MarkAsReadUseCase(notificationAccessControlService, notificationRepositoryPort),
                new DeleteNotificationUseCase(notificationAccessControlService, notificationRepositoryPort)
        );
    }

    @Bean
    NotificationEventListenerAdapter notificationEventListener(
            NotificationRepositoryPort notificationRepositoryPort,
            SmsNotificationPort smsNotificationPort,
            EmailNotificationPort emailNotificationPort,
            SystemNotificationPort systemNotificationPort,
            NotificationPreferencesRepositoryPort notificationPreferencesPort,
            EventPublisherPort publisherPort,
            MessageProviderPort messageProviderPort
    ) {
        return new NotificationEventListenerAdapter(
                notificationRepositoryPort,
                messageProviderPort,
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
