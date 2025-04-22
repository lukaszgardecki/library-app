package com.example.userservice.user.core;

import com.example.userservice.common.Constants;
import com.example.userservice.common.MessageKey;
import com.example.userservice.user.domain.exceptions.UserException;
import com.example.userservice.user.domain.model.user.User;
import com.example.userservice.user.domain.model.user.UserId;
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
