package com.example.userservice.core.user;

import com.example.userservice.domain.Constants;
import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.exception.UserException;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyUserForBookItemRequestUseCase {
    private final UserService userService;

    void execute(UserId userId) {
        User user = userService.getUserById(userId);
        if (user.getTotalBooksRequested().value() >= Constants.MAX_BOOKS_REQUESTED_BY_USER) {
            throw new UserException(MessageKey.REQUEST_LIMIT_EXCEEDED);
        }
    }
}
