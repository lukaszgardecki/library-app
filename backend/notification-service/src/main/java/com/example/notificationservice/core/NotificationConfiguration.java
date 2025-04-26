package com.example.notificationservice.core;

import com.example.notificationservice.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

    @Bean
    NotificationFacade notificationFacade(
            NotificationRepositoryPort notificationRepositoryPort
    ) {
        NotificationService notificationService = new NotificationService(notificationRepositoryPort);
        NotificationAccessControlService notificationAccessControlService =
                new NotificationAccessControlService(notificationService);
        return new NotificationFacade(
                new GetPageOfNotificationsByUserIdUseCase(notificationAccessControlService, notificationRepositoryPort),
                new GetNotificationUseCase(notificationAccessControlService, notificationService),
                new MarkAsReadUseCase(notificationAccessControlService, notificationRepositoryPort),
                new DeleteNotificationUseCase(notificationAccessControlService, notificationRepositoryPort)
        );
    }

    @Bean
    EventListenerPort eventListenerService(
            NotificationRepositoryPort notificationRepositoryPort,
            CatalogServicePort catalogService,
            SmsNotificationPort smsNotificationPort,
            EmailNotificationPort emailNotificationPort,
            SystemNotificationPort systemNotificationPort,
            NotificationPreferencesRepositoryPort notificationPreferencesPort,
            EventPublisherPort publisherPort,
            MessageProviderPort messageProviderPort
    ) {
        return new EventListenerService(
                notificationRepositoryPort,
                new NotificationFactory(messageProviderPort, catalogService),
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
