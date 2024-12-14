package com.example.libraryapp.NEWinfrastructure.persistence.jpa.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    @Query("""
        select t from RefreshTokenEntity t
        where t.userId = :userId
            and (t.expired = false or t.revoked = false)
        """)
    List<RefreshTokenEntity> findAllValidTokenByUser(Long userId);

    Optional<RefreshTokenEntity> findByToken(String token);

    @Query("""
            SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
            FROM RefreshTokenEntity t
            WHERE t.token = :token AND t.expired = false AND t.revoked = false
            """)
    boolean existsValidToken(String token);
}
