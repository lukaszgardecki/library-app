package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.UserListPreview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class SearchUserPreviewsUseCase {
    private final UserService userService;

    Page<UserListPreview> execute(String query, Pageable pageable) {
        return userService.getUserPreviewsByQuery(query, pageable);
    }
}
