package com.example.libraryapp.domain.user.mapper;

import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.dto.UserDto;

public class UserDtoMapper {

    public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setCard(user.getCard());
        return dto;
    }
}
