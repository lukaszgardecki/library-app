package com.example.userservice.core.user;


import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.BookItemRequestServicePort;
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
