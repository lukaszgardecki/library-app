package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.model.values.UserId;

public interface FineServicePort {

    void verifyUserForFines(UserId userId);
}
