package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.NEWdomain.user.model.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class VerifyUserForBookItemRenewalUseCase {
    private final UserService userService;

    void execute(Long userId) {
        User user = userService.getUser(userId);
        if (user.getCharge().compareTo(BigDecimal.ZERO) > 0) throw new UnsettledFineException();
    }
}
