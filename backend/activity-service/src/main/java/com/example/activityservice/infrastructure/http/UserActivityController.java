package com.example.activityservice.infrastructure.http;

import com.example.activityservice.core.UserActivityFacade;
import com.example.activityservice.domain.dto.UserActivityDto;
import com.example.activityservice.domain.model.values.ActivityId;
import com.example.activityservice.domain.model.values.UserId;
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
class UserActivityController {
    private final UserActivityFacade userActivityFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    public ResponseEntity<Page<UserActivityDto>> getAllActions(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String type,
            Pageable pageable
    ) {
        Page<UserActivityDto> page = userActivityFacade.getPageOfActivities(new UserId(userId), type, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserActivityDto> getActionById(@PathVariable Long id) {
        UserActivityDto activity = userActivityFacade.getActivity(new ActivityId(id));
//        authFacade.validateOwnerOrAdminAccess(new UserId(activity.getUserId()));
        return ResponseEntity.ok(activity);
    }
}
