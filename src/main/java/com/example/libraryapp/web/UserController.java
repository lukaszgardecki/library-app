package com.example.libraryapp.web;

import com.example.libraryapp.domain.exception.UserNotFoundException;
import com.example.libraryapp.domain.user.UserService;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedModel<UserDto>> getAllUsers(Pageable pageable) {
        PagedModel<UserDto> collectionModel = userService.findAllUsers(pageable);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .filter(user -> userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(user.getId()))
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(id);
        if (userIsAdminOrDataOwner) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PatchMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        boolean userIsAdminOrDataOwner = userService.checkIfCurrentLoggedInUserIsAdminOrDataOwner(id);
        if (userIsAdminOrDataOwner) {
            UserDto updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
