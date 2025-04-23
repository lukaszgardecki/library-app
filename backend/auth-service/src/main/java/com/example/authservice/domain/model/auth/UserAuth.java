package com.example.authservice.domain.model.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {
    private AuthId id;
    private Password psswrd;
    private Email email;
    private AccountStatus status;
    private Role role;
    private UserId userId;
}
