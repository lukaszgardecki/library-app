package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUsersByLoanCountDescendingUseCase {
    private final UserService userService;

    List<User> execute(int limit) {
        return userService.getAllByLoansCountDesc(limit);
    }
}