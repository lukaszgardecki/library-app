package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.model.token.Token;

import java.util.List;
import java.util.Optional;

public interface AccessTokenRepositoryPort {

    void save(Token token);
    void saveAll(List<Token> tokens);
    Optional<Token> findByToken(String token);
    List<Token> findAllValidTokensByUserId(UserId userId);
}
