package com.example.libraryapp.infrastructure.spring.security.auth;

import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.Password;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserId;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails, CredentialsContainer {
    private final UserId id;
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
        this.id = user.getId();
    }

    public UserId getId() {
        return id;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPsswrd().value();
    }

    @Override
    public String getUsername() {
        return user.getEmail().value();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getStatus().equals(AccountStatus.INACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getStatus().equals(AccountStatus.SUSPENDED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(AccountStatus.ACTIVE);
    }

    @Override
    public void eraseCredentials() {
        user.setPsswrd(new Password("null"));
    }
}
