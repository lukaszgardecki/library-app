package com.example.authservice.infrastructure.security.auth;

import com.example.authservice.domain.model.authdetails.*;
import com.example.authservice.domain.model.authdetails.values.AccountStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomAuthDetails implements UserDetails, CredentialsContainer {
    private final AuthDetails auth;

    public Long getUserId() {
        return auth.getUserId().value();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + auth.getRole().name()));
    }

    @Override
    public String getPassword() {
        return auth.getPsswrd().value();
    }

    @Override
    public String getUsername() {
        return auth.getEmail().value();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !auth.getStatus().equals(AccountStatus.INACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !auth.getStatus().equals(AccountStatus.SUSPENDED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return auth.getStatus().equals(AccountStatus.ACTIVE);
    }

    @Override
    public void eraseCredentials() {
        auth.setPsswrd(null);
    }
}
