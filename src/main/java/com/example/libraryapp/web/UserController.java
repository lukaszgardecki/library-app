package com.example.libraryapp.web;

import com.example.libraryapp.domain.config.assembler.UserModelAssembler;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService,
                          UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/users")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        List<UserDto> allUsers = userService.findAllUsers();
        if (allUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        CollectionModel<EntityModel<UserDto>> collectionModel = userModelAssembler.toCollectionModel(allUsers);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        try {
            UserDto updatedUser = userService.updateUser(id, user);
            EntityModel<UserDto> entityModel = userModelAssembler.toModel(updatedUser);
            return ResponseEntity.ok(entityModel);
        } catch (UserNotFoundException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
