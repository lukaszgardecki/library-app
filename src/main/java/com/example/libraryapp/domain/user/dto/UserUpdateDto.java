package com.example.libraryapp.domain.user.dto;

import com.example.libraryapp.domain.card.LibraryCard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LibraryCard card;
    private String role;
}
