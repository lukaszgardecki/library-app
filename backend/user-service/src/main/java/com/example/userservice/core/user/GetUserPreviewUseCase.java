package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.model.user.UserPreview;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserPreviewUseCase {
    private final UserService userService;

    UserPreview execute(UserId id) {
        return userService.getUserPreview(id);
    }
}
