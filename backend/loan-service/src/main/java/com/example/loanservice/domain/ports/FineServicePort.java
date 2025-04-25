package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.model.UserId;

public interface FineServicePort {

    void verifyUserForFines(UserId userId);
}
