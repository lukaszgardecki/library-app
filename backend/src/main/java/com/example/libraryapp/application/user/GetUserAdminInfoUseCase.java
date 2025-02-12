package com.example.libraryapp.application.user;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.user.model.AdminUserDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserAdminInfoUseCase {
    private final UserService userService;
    private final AuthenticationFacade authFacade;

    AdminUserDetails execute(Long id) {
        authFacade.validateAdminAccess();
        return userService.getAdminUserDetailsById(id);
    }
}
