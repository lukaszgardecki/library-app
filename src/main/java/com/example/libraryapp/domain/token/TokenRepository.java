package com.example.libraryapp.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
        select t from Token t
        inner join Member u
        on t.member.id = u.id
        where u.id = :userId
        and (t.expired = false or t.revoked = false)
        """)
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);
}
