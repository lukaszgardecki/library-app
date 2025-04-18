package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.domain.useractivity.model.UserActivityId;
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

    public UserActivityDto getActivity(UserActivityId id) {
        return UserActivityMapper.toDto(getActivityUseCase.execute(id));
    }

    public UserActivityDto saveActivity(UserActivityDto activity) {
        return UserActivityMapper.toDto(saveActivityUseCase.execute(activity));
    }
}
