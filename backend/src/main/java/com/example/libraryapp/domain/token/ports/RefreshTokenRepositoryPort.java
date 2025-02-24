package com.example.libraryapp.domain.token.ports;

import com.example.libraryapp.domain.token.model.Token;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepositoryPort {

    void save(Token token);
    void saveAll(List<Token> tokens);
    Optional<Token> findByToken(String token);
    List<Token> findAllValidTokensByUserId(Long userId);
    boolean existsValidToken(String token);
}
