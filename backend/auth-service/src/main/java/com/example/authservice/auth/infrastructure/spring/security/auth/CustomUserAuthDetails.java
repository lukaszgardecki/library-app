package com.example.authservice.auth.infrastructure.spring.security.auth;

import com.example.authservice.auth.domain.model.AccountStatus;
import com.example.authservice.auth.domain.model.UserAuth;
import com.example.authservice.auth.domain.model.Password;
import com.example.authservice.auth.domain.model.UserId;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserAuthDetails extends UserAuth implements UserDetails, CredentialsContainer {
    private final UserId userId;
    private final UserAuth userAuth;

    public CustomUserAuthDetails(UserAuth userAuth) {
        this.userAuth = userAuth;
        this.userId = userAuth.getUserId();
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userAuth.getRole().name()));
    }

    @Override
    public String getPassword() {
        return userAuth.getPsswrd().value();
    }

    @Override
    public String getUsername() {
        return userAuth.getEmail().value();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userAuth.getStatus().equals(AccountStatus.INACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userAuth.getStatus().equals(AccountStatus.SUSPENDED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userAuth.getStatus().equals(AccountStatus.ACTIVE);
    }

    @Override
    public void eraseCredentials() {
        userAuth.setPsswrd(new Password("null"));
    }
}
