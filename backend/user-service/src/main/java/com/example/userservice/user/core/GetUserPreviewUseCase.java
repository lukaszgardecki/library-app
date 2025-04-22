package com.example.userservice.user.core;

import com.example.userservice.user.domain.model.user.UserId;
import com.example.userservice.user.domain.model.user.UserPreview;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserPreviewUseCase {
    private final UserService userService;

    UserPreview execute(UserId id) {
        return userService.getUserPreview(id);
    }
}
