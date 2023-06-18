package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private static final String BOOKS_NOT_RETURNED_MSG = "User's books are not returned.";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<PagedModel<UserDto>> getAllUsers(Pageable pageable) {
        PagedModel<UserDto> collectionModel = userService.findAllUsers(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .filter(user -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(user.getId()))
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
                return ResponseEntity.ok(updatedUser);
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
