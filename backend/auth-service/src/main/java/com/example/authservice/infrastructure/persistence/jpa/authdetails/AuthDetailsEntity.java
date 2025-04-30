package com.example.authservice.infrastructure.persistence.jpa.authdetails;

import com.example.authservice.domain.model.authdetails.AccountStatus;
import com.example.authservice.domain.model.authdetails.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AuthDetailsEntity {
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