package com.example.libraryapp.NEWapplication.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.dto.UserActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserActivityFacade {
    private final GetPageOfActivitiesByParamsUseCase getPageOfActivitiesByParamsUseCase;
    private final GetActivityUseCase getActivityUseCase;
    private final SaveActivityUseCase saveActivityUseCase;

    public Page<UserActivityDto> getPageOfActivities(Long userId, String type, Pageable pageable) {
        return getPageOfActivitiesByParamsUseCase.execute(userId, type, pageable).map(UserActivityMapper::toDto);
    }

    public UserActivityDto getActivity(Long id) {
        return UserActivityMapper.toDto(getActivityUseCase.execute(id));
    }

    public UserActivityDto saveActivity(UserActivityDto activity) {
        return UserActivityMapper.toDto(saveActivityUseCase.execute(activity));
    }
}
