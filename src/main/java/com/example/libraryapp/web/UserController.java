package com.example.libraryapp.web;

import com.example.libraryapp.domain.config.assembler.UserModelAssembler;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static final String BOOKS_NOT_RETURNED_MSG = "User's books are not returned.";
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/users")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        return userService.findAllUsers()
            .map(userModelAssembler::toCollectionModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .filter(user -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(user.getId()))
                .map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(id);
            if (userIsAdminOrDataOwner) {
                userService.deleteUserById(id);
                return ResponseEntity.noContent().build();
            }
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UserHasNotReturnedBooksException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .header("Reason", BOOKS_NOT_RETURNED_MSG)
                    .build();
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        try {
            boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(id);
            if (userIsAdminOrDataOwner) {
                UserDto updatedUser = userService.updateUser(id, user);
                EntityModel<UserDto> entityModel = userModelAssembler.toModel(updatedUser);
                return ResponseEntity.ok(entityModel);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch (UserNotFoundException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> mismatchExceptionHandler() {
        return ResponseEntity.badRequest().body("Id must be a number.");
    }
}
