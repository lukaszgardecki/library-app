package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.auth.UserAuth;
import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.model.auth.UserId;

import java.util.Optional;

public interface UserAuthRepositoryPort {
    boolean existsByEmail(Email email);

    Optional<UserAuth> findByEmail(Email email);

    Optional<UserAuth> findByUserId(UserId userId);

    void save(UserAuth build);

}
