package com.example.libraryapp.web;

import com.example.libraryapp.domain.config.CustomSecurityConfig;
import com.example.libraryapp.domain.config.assembler.UserModelAssembler;
import com.example.libraryapp.domain.exception.CannotUpdateUserDataException;
import com.example.libraryapp.domain.exception.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
        CollectionModel<EntityModel<UserDto>> collectionModel = userModelAssembler.toCollectionModel(allUsers);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUserById(@PathVariable Long id) {
        try {
            boolean requestFromOwner = userService.getCurrentLoggedInUserId() == id;
            boolean requestFromAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);

            if (requestFromOwner || requestFromAdmin) {
                return userService.findUserById(id)
                        .map(userModelAssembler::toModel)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
            } else throw new UserNotFoundException();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            boolean requestFromOwner = userService.getCurrentLoggedInUserId() == id;
            boolean requestFromAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);

            if (requestFromOwner || requestFromAdmin) {
                userService.deleteUserById(id);
                return ResponseEntity.noContent().build();
            } else throw new CannotUpdateUserDataException();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CannotUpdateUserDataException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (UserHasNotReturnedBooksException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Reason", "User's books are not returned.")
                    .build();
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        try {
            boolean isDataOwner = userService.getCurrentLoggedInUserId() == id;
            boolean isAdmin = userService.getCurrentLoggedInUserRole().equals(CustomSecurityConfig.ADMIN_ROLE);

            if (isDataOwner || isAdmin) {
                UserDto updatedUser = userService.updateUser(id, user);
                EntityModel<UserDto> entityModel = userModelAssembler.toModel(updatedUser);
                return ResponseEntity.ok(entityModel);
            } else throw new CannotUpdateUserDataException();
        } catch (UserNotFoundException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (CannotUpdateUserDataException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
