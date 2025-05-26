package com.example.userservice.infrastructure.http.user;

import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.core.user.UserFacade;
import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.http.user.dto.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/register")
@RequiredArgsConstructor
class RegisterController {
    private final UserFacade userFacade;
    private final PersonFacade personFacade;

    @PostMapping
    ResponseEntity<Long> register(@RequestBody RegisterUserDto body) {
        Person savedPerson = personFacade.save(PersonMapper.toModel(body));
        UserId userId = userFacade.createNewUser(savedPerson.getId());
        return ResponseEntity.ok(userId.value());
    }
}
