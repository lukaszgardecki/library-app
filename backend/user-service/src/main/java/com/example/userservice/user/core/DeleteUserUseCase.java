package com.example.userservice.user.core;


import com.example.userservice.user.domain.model.user.UserId;
import com.example.userservice.user.domain.ports.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteUserUseCase {
    private final UserService userService;
    private final BookItemRequestServicePort bookItemRequestService;

    void execute(UserId userId) {
        userService.validateUserToDelete(userId);
        bookItemRequestService.cancelAllItemRequestsByUserId(userId);
        userService.deleteById(userId);
    }
}
