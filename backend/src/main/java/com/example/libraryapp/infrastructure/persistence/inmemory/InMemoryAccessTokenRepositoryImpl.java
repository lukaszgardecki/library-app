package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.token.model.Token;
import com.example.libraryapp.domain.token.ports.AccessTokenRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;


@RequiredArgsConstructor
public class InMemoryAccessTokenRepositoryImpl implements AccessTokenRepository {
    private final ConcurrentHashMap<Long, Token> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public void save(Token token) {
        requireNonNull(token, "Token cannot be null");
        if (token.getId() == null) {
            token.setId(++id);
        }
        map.put(token.getId(), token);
    }

    @Override
    public void saveAll(List<Token> tokens) {
        tokens.forEach(token -> map.put(token.getId(), token));
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return map.values().stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst();
    }

    @Override
    public List<Token> findAllValidTokensByUserId(Long userId) {
        return map.values().stream()
                .filter(t -> t.getUserId().equals(userId))
                .toList();
    }

    @Override
    public boolean existsValidToken(String token) {
        return map.values().stream()
                .anyMatch(t -> t.getToken().equals(token));
    }
}
