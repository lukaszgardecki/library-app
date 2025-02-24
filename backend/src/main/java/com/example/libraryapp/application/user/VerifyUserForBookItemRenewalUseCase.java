package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class VerifyUserForBookItemRenewalUseCase {
    private final UserService userService;

    void execute(Long userId) {
        User user = userService.getUserById(userId);
        if (user.getCharge().compareTo(BigDecimal.ZERO) > 0) throw new UnsettledFineException();
    }
}
