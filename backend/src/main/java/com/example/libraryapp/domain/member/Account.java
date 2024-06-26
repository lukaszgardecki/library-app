package com.example.libraryapp.domain.member;

import com.example.libraryapp.domain.token.AccessToken;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class Account implements UserDetails {
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<AccessToken> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
