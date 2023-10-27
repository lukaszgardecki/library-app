package com.example.libraryapp.domain.user;

import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.lending.Lending;
import com.example.libraryapp.domain.reservation.Reservation;
import com.example.libraryapp.domain.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.REMOVE)
    private LibraryCard card;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Lending> lendings;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public void cancelAllReservations() {
        reservations.forEach(res -> res.getBook().setAvailability(true));
    }

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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}