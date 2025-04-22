package com.example.libraryapp.infrastructure.http;

import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.core.useractivity.UserActivityFacade;
import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.domain.useractivity.model.UserActivityId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/activities")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequiredArgsConstructor
class UserActivityController {
    private final UserActivityFacade userActivityFacade;
    private final AuthenticationFacade authFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
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
        UserActivityDto activity = userActivityFacade.getActivity(new UserActivityId(id));
        authFacade.validateOwnerOrAdminAccess(new UserId(activity.getUserId()));
        return ResponseEntity.ok(activity);
    }
}
