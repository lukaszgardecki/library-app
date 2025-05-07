package com.example.authservice.domain.model.authdetails;

import com.example.authservice.domain.model.authdetails.values.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetails {
    private AuthDetailsId id;
    private Password psswrd;
    private Email email;
    private AccountStatus status;
    private Role role;
    private UserId userId;
}
