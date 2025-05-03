package com.example.authservice.infrastructure.persistence.jpa.token.refreshtoken;

import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.model.token.Token;
import com.example.authservice.domain.ports.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {
    private final JpaRefreshTokenRepository repository;

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
    public List<Token> findAllValidTokensByUserId(UserId userId) {
        return repository.findAllValidTokensByUserId(userId.value()).stream()
                .map(this::toModel)
                .toList();
    }

    private RefreshTokenEntity toEntity(Token model) {
        return new RefreshTokenEntity(
                model.getId(),
                model.getToken(),
                model.getTokenType(),
                model.isExpired(),
                model.isRevoked(),
                model.getUserId()
        );
    }

    private Token toModel(RefreshTokenEntity entity) {
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
