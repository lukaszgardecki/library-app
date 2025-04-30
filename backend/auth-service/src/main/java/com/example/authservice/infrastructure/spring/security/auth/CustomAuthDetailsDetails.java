package com.example.authservice.infrastructure.spring.security.auth;

import com.example.authservice.domain.model.authdetails.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomAuthDetailsDetails extends AuthDetails implements UserDetails, CredentialsContainer {
    private UserId userId;
    private AuthDetailsId authId;
    private Password psswrd;
    private Email email;
    private AccountStatus status;
    private Role role;

    public CustomAuthDetailsDetails(AuthDetails authDetails) {
        this.userId = authDetails.getUserId();
        this.authId = authDetails.getId();
        this.psswrd = authDetails.getPsswrd();
        this.email = authDetails.getEmail();
        this.status = authDetails.getStatus();
        this.role = authDetails.getRole();
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return psswrd.value();
    }

    @Override
    public String getUsername() {
        return email.value();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !status.equals(AccountStatus.INACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status.equals(AccountStatus.SUSPENDED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(AccountStatus.ACTIVE);
    }

    @Override
    public void eraseCredentials() {
        this.psswrd = null;
    }
}
