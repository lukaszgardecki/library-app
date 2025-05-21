package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.model.values.UserId;

public interface UserServicePort {

    void verifyUserForBookItemLoan(UserId userId);

    void verifyUserForBookItemRenewal(UserId userId);
}
