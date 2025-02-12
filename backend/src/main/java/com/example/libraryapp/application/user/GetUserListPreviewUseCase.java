package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.UserListPreview;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserListPreviewUseCase {
    private final UserService userService;

    UserListPreview execute(Long userId) {
        return userService.getListPreviewById(userId);
    }
}
