package com.example.libraryapp.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    @Query("""
        select t from AccessToken t
        inner join Member u
        on t.member.id = u.id
        where u.id = :userId
        and (t.expired = false or t.revoked = false)
        """)
    List<AccessToken> findAllValidTokenByUser(Long userId);

    Optional<AccessToken> findByToken(String token);

    @Query("""
            SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
            FROM AccessToken t
            WHERE t.token = :token AND t.expired = false AND t.revoked = false
            """)
    boolean existsValidToken(String token);
}
