package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.useractivity.ports.UserActivityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserActivityConfiguration {

    @Bean
    UserActivityFacade activityFacade(UserActivityRepository userActivityRepository) {
        return new UserActivityFacade(
                new GetPageOfActivitiesByParamsUseCase(userActivityRepository),
                new GetActivityUseCase(userActivityRepository),
                new SaveActivityUseCase(userActivityRepository)
        );
    }

    @Bean
    UserActivityEventListenerImpl activityEventListener(UserActivityFacade userActivityFacade) {
        return new UserActivityEventListenerImpl(userActivityFacade);
    }
}
