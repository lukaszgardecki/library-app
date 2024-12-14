package com.example.libraryapp.NEWinfrastructure.persistence.jpa.refreshtoken;

import com.example.libraryapp.NEWdomain.token.model.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class RefreshTokenEntity {
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
