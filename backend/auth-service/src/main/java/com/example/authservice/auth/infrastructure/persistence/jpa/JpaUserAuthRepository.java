package com.example.authservice.auth.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserAuthEntity> findByEmail(String email);

    Optional<UserAuthEntity> findByUserId(Long userId);
}
