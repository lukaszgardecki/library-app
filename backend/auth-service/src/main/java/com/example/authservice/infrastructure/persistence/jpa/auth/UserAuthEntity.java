package com.example.authservice.infrastructure.persistence.jpa.auth;

import com.example.authservice.domain.model.auth.AccountStatus;
import com.example.authservice.domain.model.auth.Role;
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