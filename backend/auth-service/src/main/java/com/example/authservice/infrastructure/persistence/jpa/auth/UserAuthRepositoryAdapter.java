package com.example.authservice.infrastructure.persistence.jpa.auth;

import com.example.authservice.domain.ports.UserAuthRepositoryPort;
import com.example.authservice.domain.model.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
class UserAuthRepositoryAdapter implements UserAuthRepositoryPort {
    private final JpaUserAuthRepository repository;

    @Override
    public boolean existsByEmail(Email email) {
        return repository.existsByEmail(email.value());
    }

    @Override
    public Optional<UserAuth> findByEmail(Email email) {
        return repository.findByEmail(email.value()).map(this::toModel);
    }

    @Override
    public Optional<UserAuth> findByUserId(UserId userId) {
        return repository.findByUserId(userId.value()).map(this::toModel);
    }

    @Override
    public void save(UserAuth userAuth) {
        repository.save(toEntity(userAuth));
    }


    private UserAuthEntity toEntity(UserAuth model) {
        return new UserAuthEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getPsswrd().value(),
                model.getEmail().value(),
                model.getStatus(),
                model.getRole(),
                model.getUserId().value()
        );
    }

    private UserAuth toModel(UserAuthEntity entity) {
        return new UserAuth(
                new AuthId(entity.getId()),
                new Password(entity.getPassword()),
                new Email(entity.getEmail()),
                entity.getStatus(),
                entity.getRole(),
                new UserId(entity.getUserId())
        );
    }
}
