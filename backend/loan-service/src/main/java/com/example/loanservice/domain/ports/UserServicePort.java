package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.model.UserId;

public interface UserServicePort {

    void verifyUserForBookItemLoan(UserId userId);

    void verifyUserForBookItemRenewal(UserId userId);
}
