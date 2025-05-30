package com.example.activityservice.core;

import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;
import com.example.activityservice.domain.ports.in.EventListenerPort;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.out.SourceValidatorPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ActivityConfiguration {

    @Bean
    ActivityFacade activityFacade(
            ActivityRepositoryPort activityRepository,
            SourceValidatorPort sourceValidator
    ) {
        return new ActivityFacade(
                new GetPageOfActivitiesByParamsUseCase(activityRepository),
                new GetActivityUseCase(activityRepository, sourceValidator),
                new SaveActivityUseCase(activityRepository)
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
