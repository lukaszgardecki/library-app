package com.example.libraryapp.core.useractivity;

import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserActivityConfiguration {

    @Bean
    UserActivityFacade activityFacade(UserActivityRepositoryPort userActivityRepository) {
        return new UserActivityFacade(
                new GetPageOfActivitiesByParamsUseCase(userActivityRepository),
                new GetActivityUseCase(userActivityRepository),
                new SaveActivityUseCase(userActivityRepository)
        );
    }

    @Bean
    UserActivityEventListenerAdapter activityEventListener(
            UserActivityFacade userActivityFacade,
            MessageProviderPort messageProvider
    ) {
        return new UserActivityEventListenerAdapter(userActivityFacade, messageProvider);
    }
}
