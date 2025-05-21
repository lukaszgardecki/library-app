package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUsersByLoanCountDescendingUseCase {
    private final UserService userService;

    List<User> execute(int limit) {
        return userService.getAllByLoansCountDesc(limit);
    }
}