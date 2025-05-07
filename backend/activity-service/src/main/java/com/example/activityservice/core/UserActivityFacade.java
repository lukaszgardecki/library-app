package com.example.activityservice.core;

import com.example.activityservice.domain.dto.UserActivityDto;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserActivityFacade {
    private final GetPageOfActivitiesByParamsUseCase getPageOfActivitiesByParamsUseCase;
    private final GetActivityUseCase getActivityUseCase;
    private final SaveActivityUseCase saveActivityUseCase;

    public Page<UserActivityDto> getPageOfActivities(UserId userId, String type, Pageable pageable) {
        return getPageOfActivitiesByParamsUseCase.execute(userId, type, pageable).map(UserActivityMapper::toDto);
    }

    public UserActivityDto getActivity(ActivityId id) {
        return UserActivityMapper.toDto(getActivityUseCase.execute(id));
    }

    public UserActivityDto saveActivity(UserActivityDto activity) {
        return UserActivityMapper.toDto(saveActivityUseCase.execute(activity));
    }
}
