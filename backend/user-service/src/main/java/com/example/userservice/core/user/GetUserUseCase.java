package com.example.userservice.core.user;

import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserUseCase {
    private final UserService userService;

    User execute(UserId id) {
        return userService.getUserById(id);
    }

    User execute(PersonId id) {
        return userService.getUserByPersonId(id);
    }
}
