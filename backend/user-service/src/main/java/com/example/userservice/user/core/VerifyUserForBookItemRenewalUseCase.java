package com.example.userservice.user.core;

import com.example.userservice.user.domain.exceptions.UnsettledFineException;
import com.example.userservice.user.domain.model.user.User;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class VerifyUserForBookItemRenewalUseCase {
    private final UserService userService;

    void execute(UserId userId) {
        User user = userService.getUserById(userId);
        if (user.getCharge().value().compareTo(BigDecimal.ZERO) > 0) throw new UnsettledFineException();
    }
}
