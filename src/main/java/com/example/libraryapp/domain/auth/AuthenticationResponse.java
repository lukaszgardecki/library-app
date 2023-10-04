package com.example.libraryapp.domain.auth;

import com.example.libraryapp.domain.card.LibraryCard;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LibraryCard card;
    private String token;
}
