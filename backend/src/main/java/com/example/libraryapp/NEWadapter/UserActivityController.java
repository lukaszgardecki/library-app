package com.example.libraryapp.NEWadapter;

import com.example.libraryapp.NEWapplication.useractivity.UserActivityFacade;
import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWdomain.useractivity.dto.UserActivityDto;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.NEWdomain.user.model.Role.ADMIN;
import static com.example.libraryapp.NEWdomain.user.model.Role.USER;

@RestController
@RequestMapping("/api/v1/actions")
@RequiredArgsConstructor
class UserActivityController {
    private final UserActivityFacade userActivityFacade;
    private final AuthenticationFacade authFacade;

    @GetMapping
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<Page<UserActivityDto>> getAllActions(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) String type,
            Pageable pageable
    ) {
        authFacade.validateOwnerOrAdminAccess(memberId);
        Page<UserActivityDto> page = userActivityFacade.getPageOfActivities(memberId, type, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<UserActivityDto> getActionById(@PathVariable Long id) {
        UserActivityDto activity = userActivityFacade.getActivity(id);
        authFacade.validateOwnerOrAdminAccess(activity.getUserId());
        return ResponseEntity.ok(activity);
    }
}
