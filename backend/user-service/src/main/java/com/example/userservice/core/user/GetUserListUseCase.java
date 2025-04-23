package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.UserListPreviewProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetUserListUseCase {
    private final UserService userService;

    Page<UserListPreviewProjection> execute(String query, Pageable pageable) {
        return userService.getUserPreviewsByQuery(query, pageable);
    }
}
