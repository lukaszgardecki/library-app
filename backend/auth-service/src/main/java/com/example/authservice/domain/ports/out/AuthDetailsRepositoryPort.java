package com.example.authservice.domain.ports.out;

import com.example.authservice.domain.model.authdetails.AuthDetails;
import com.example.authservice.domain.model.authdetails.values.AuthDetailsId;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.UserId;

import java.util.Optional;

public interface AuthDetailsRepositoryPort {
    boolean existsByEmail(Email email);

    Optional<AuthDetails> findById(AuthDetailsId authId);

    Optional<AuthDetails> findByEmail(Email email);

    Optional<AuthDetails> findByUserId(UserId userId);

    void save(AuthDetails build);

}
