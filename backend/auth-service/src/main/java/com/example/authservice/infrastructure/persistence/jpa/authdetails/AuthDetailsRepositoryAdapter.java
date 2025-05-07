package com.example.authservice.infrastructure.persistence.jpa.authdetails;

import com.example.authservice.domain.model.authdetails.*;
import com.example.authservice.domain.model.authdetails.values.AuthDetailsId;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.ports.out.AuthDetailsRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class AuthDetailsRepositoryAdapter implements AuthDetailsRepositoryPort {
    private final JpaAuthDetailsRepository repository;

    @Override
    public boolean existsByEmail(Email email) {
        return repository.existsByEmail(email.value());
    }

    @Override
    public Optional<AuthDetails> findById(AuthDetailsId authId) {
        return repository.findById(authId.value()).map(this::toModel);
    }

    @Override
    public Optional<AuthDetails> findByEmail(Email email) {
        return repository.findByEmail(email.value()).map(this::toModel);
    }

    @Override
    public Optional<AuthDetails> findByUserId(UserId userId) {
        return repository.findByUserId(userId.value()).map(this::toModel);
    }

    @Override
    public void save(AuthDetails authDetails) {
        repository.save(toEntity(authDetails));
    }


    private AuthDetailsEntity toEntity(AuthDetails model) {
        return new AuthDetailsEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getPsswrd().value(),
                model.getEmail().value(),
                model.getStatus(),
                model.getRole(),
                model.getUserId().value()
        );
    }

    private AuthDetails toModel(AuthDetailsEntity entity) {
        return new AuthDetails(
                new AuthDetailsId(entity.getId()),
                new Password(entity.getPassword()),
                new Email(entity.getEmail()),
                entity.getStatus(),
                entity.getRole(),
                new UserId(entity.getUserId())
        );
    }
}
