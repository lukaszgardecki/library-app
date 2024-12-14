package com.example.libraryapp.NEWdomain.token.ports;

import com.example.libraryapp.NEWdomain.token.model.Token;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository {

    void save(Token token);
    void saveAll(List<Token> tokens);
    Optional<Token> findByToken(String token);
    List<Token> findAllValidTokenByUser(Long userId);
    boolean existsValidToken(String token);
}
