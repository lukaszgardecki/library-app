package com.example.authservice.infrastructure.persistence.jpa.token.accesstoken;

import com.example.authservice.domain.model.token.Token;
import com.example.authservice.domain.ports.AccessTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class AccessTokenRepositoryAdapter implements AccessTokenRepositoryPort {
    private final JpaAccessTokenRepository repository;

    @Override
    @Transactional
    public void save(Token token) {
        repository.save(toEntity(token));
    }

    @Override
    @Transactional
    public void saveAll(List<Token> tokens) {
        repository.saveAll(
                tokens.stream().map(this::toEntity).toList()
        );
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken(token).map(this::toModel);
    }

    @Override
    public List<Token> findAllValidTokensByUserId(Long userId) {
        return repository.findAllValidTokensByUserId(userId).stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public boolean existsValidToken(String token) {
        return repository.existsValidToken(token);
    }

    private AccessTokenEntity toEntity(Token model) {
        return new AccessTokenEntity(
                model.getId(),
                model.getToken(),
                model.getTokenType(),
                model.isExpired(),
                model.isRevoked(),
                model.getUserId()
        );
    }

    private Token toModel(AccessTokenEntity entity) {
        return new Token(
            entity.getId(),
            entity.getToken(),
            entity.getTokenType(),
            entity.isExpired(),
            entity.isRevoked(),
            entity.getUserId()
        );
    }
}
