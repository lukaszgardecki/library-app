package com.example.libraryapp.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cardNumber;
}
