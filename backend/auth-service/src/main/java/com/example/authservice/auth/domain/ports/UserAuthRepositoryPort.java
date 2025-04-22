package com.example.authservice.auth.domain.ports;

import com.example.authservice.auth.domain.model.UserAuth;
import com.example.authservice.auth.domain.model.Email;
import com.example.authservice.auth.domain.model.UserId;

import java.util.Optional;

public interface UserAuthRepositoryPort {
    boolean existsByEmail(Email email);

    Optional<UserAuth> findByEmail(Email email);

    Optional<UserAuth> findByUserId(UserId userId);

    void save(UserAuth build);

}
