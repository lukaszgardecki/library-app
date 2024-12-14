package com.example.libraryapp.NEWinfrastructure.persistence.jpa.accesstoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface JpaAccessTokenRepository extends JpaRepository<AccessTokenEntity, Long> {

    @Query("""
        select t from AccessTokenEntity t
        where t.userId = :userId
            and (t.expired = false or t.revoked = false)
        """)
    List<AccessTokenEntity> findAllValidTokenByUser(Long userId);

    Optional<AccessTokenEntity> findByToken(String token);

    @Query("""
            SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
            FROM AccessTokenEntity t
            WHERE t.token = :token AND t.expired = false AND t.revoked = false
            """)
    boolean existsValidToken(String token);
}
