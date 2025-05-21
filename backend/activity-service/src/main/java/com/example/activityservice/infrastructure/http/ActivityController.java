package com.example.activityservice.infrastructure.http;

import com.example.activityservice.core.ActivityFacade;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
import com.example.activityservice.infrastructure.http.dto.ActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activities")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
class ActivityController {
    private final ActivityFacade activityFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<Page<ActivityDto>> getAllActions(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String type,
            Pageable pageable
    ) {
        Page<ActivityDto> page = activityFacade.getPageOfActivities(new UserId(userId), type, pageable)
                .map(ActivityMapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    ResponseEntity<ActivityDto> getActionById(@PathVariable Long id) {
        ActivityDto activity = ActivityMapper.toDto(activityFacade.getActivity(new ActivityId(id)));
//        authFacade.validateOwnerOrAdminAccess(new UserId(activity.getUserId()));
        return ResponseEntity.ok(activity);
    }
}
