package com.example.libraryapp.application.user;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.user.model.UserDetailsAdmin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserDetailsAdminUseCase {
    private final UserService userService;
    private final AuthenticationFacade authFacade;

    UserDetailsAdmin execute(Long id) {
        authFacade.validateAdminAccess();
        return userService.getAdminUserDetailsById(id);
    }
}
