package com.example.authservice.infrastructure.persistence.jpa.token.accesstoken;

import com.example.authservice.domain.model.token.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "access_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AccessTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;
    private boolean revoked;
    private Long userId;
}
