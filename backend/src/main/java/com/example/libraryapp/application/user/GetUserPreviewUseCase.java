package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.UserPreview;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserPreviewUseCase {
    private final UserService userService;

    UserPreview execute(Long id) {
        return userService.getUserPreview(id);
    }
}
