package com.example.libraryapp.domain.user;

import com.example.libraryapp.domain.checkout.Checkout;
import com.example.libraryapp.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cardNumber;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Checkout> checkouts;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reservation> reservations;
}