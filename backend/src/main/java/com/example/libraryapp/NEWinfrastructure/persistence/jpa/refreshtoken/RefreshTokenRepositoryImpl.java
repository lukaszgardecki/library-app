package com.example.libraryapp.NEWinfrastructure.persistence.jpa.refreshtoken;

import com.example.libraryapp.NEWdomain.token.model.Token;
import com.example.libraryapp.NEWdomain.token.ports.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
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
    public List<Token> findAllValidTokenByUser(Long userId) {
        return repository.findAllValidTokenByUser(userId).stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public boolean existsValidToken(String token) {
        return repository.existsValidToken(token);
    }

    private RefreshTokenEntity toEntity(Token dto) {
        return new RefreshTokenEntity(
                dto.getId(),
                dto.getToken(),
                dto.getTokenType(),
                dto.isExpired(),
                dto.isRevoked(),
                dto.getUserId()
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
