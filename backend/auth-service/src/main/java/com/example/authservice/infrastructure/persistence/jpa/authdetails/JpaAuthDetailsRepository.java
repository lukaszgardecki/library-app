package com.example.authservice.infrastructure.persistence.jpa.authdetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface JpaAuthDetailsRepository extends JpaRepository<AuthDetailsEntity, Long> {

    boolean existsByEmail(String email);

    Optional<AuthDetailsEntity> findByEmail(String email);

    Optional<AuthDetailsEntity> findByUserId(Long userId);
}
