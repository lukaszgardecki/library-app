package com.example.libraryapp.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("""
        select t from RefreshToken t
        inner join Member u
        on t.member.id = u.id
        where u.id = :userId
        and (t.expired = false or t.revoked = false)
        """)
    List<RefreshToken> findAllValidTokenByUser(Long userId);

    Optional<RefreshToken> findByToken(String token);

    @Query("""
            SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
            FROM RefreshToken t
            WHERE t.token = :token AND t.expired = false AND t.revoked = false
            """)
    boolean existsValidToken(String token);
}
