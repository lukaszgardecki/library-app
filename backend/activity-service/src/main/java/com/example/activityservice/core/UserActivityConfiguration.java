package com.example.activityservice.core;

import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.in.EventListenerPort;
import com.example.activityservice.domain.ports.out.SourceValidatorPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class UserActivityConfiguration {

    @Bean
    UserActivityFacade activityFacade(
            ActivityRepositoryPort userActivityRepository,
            SourceValidatorPort sourceValidator
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
            List<ActivityCreationStrategyPort<?>> strategies
    ) {
        return new EventListenerService(userActivityRepository, new ActivityFactory(strategies));
    }
}
