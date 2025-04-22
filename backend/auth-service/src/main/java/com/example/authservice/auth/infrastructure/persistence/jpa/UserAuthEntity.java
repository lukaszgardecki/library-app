package com.example.authservice.auth.infrastructure.persistence.jpa;

import com.example.authservice.auth.domain.model.AccountStatus;
import com.example.authservice.auth.domain.model.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UserAuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long userId;
}