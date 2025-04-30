package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.AuthDetailsId;
import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.UserId;

import java.util.Optional;

public interface AuthDetailsRepositoryPort {
    boolean existsByEmail(Email email);

    Optional<AuthDetails> findById(AuthDetailsId authId);

    Optional<AuthDetails> findByEmail(Email email);

    Optional<AuthDetails> findByUserId(UserId userId);

    void save(AuthDetails build);

}
