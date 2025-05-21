package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ActivityFacade {
    private final GetPageOfActivitiesByParamsUseCase getPageOfActivitiesByParamsUseCase;
    private final GetActivityUseCase getActivityUseCase;
    private final SaveActivityUseCase saveActivityUseCase;

    public Page<Activity> getPageOfActivities(UserId userId, String type, Pageable pageable) {
        return getPageOfActivitiesByParamsUseCase.execute(userId, type, pageable);
    }

    public Activity getActivity(ActivityId id) {
        return getActivityUseCase.execute(id);
    }

    public Activity saveActivity(Activity activity) {
        return saveActivityUseCase.execute(activity);
    }
}
