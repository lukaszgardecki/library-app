package com.example.activityservice.core;

import com.example.activityservice.domain.ports.ActivityCreationStrategy;
import com.example.activityservice.domain.ports.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.EventListenerPort;
import com.example.activityservice.domain.ports.SourceValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class UserActivityConfiguration {

    @Bean
    UserActivityFacade activityFacade(
            ActivityRepositoryPort userActivityRepository,
            SourceValidator sourceValidator
    ) {
        return new UserActivityFacade(
                new GetPageOfActivitiesByParamsUseCase(userActivityRepository),
                new GetActivityUseCase(userActivityRepository, sourceValidator),
                new SaveActivityUseCase(userActivityRepository)
        );
    }

    @Bean
    EventListenerPort eventListenerService(
            ActivityRepositoryPort userActivityRepository,
            List<ActivityCreationStrategy<?>> strategies
    ) {
        return new EventListenerService(userActivityRepository, new ActivityFactory(strategies));
    }
}
