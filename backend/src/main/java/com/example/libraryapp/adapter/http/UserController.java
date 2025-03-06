package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserListPreviewDto;
import com.example.libraryapp.domain.user.dto.UserPreviewDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> page = userFacade.getAllUsers(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserListPreviewDto>> getAllUserListPreviews(
            @RequestParam(value = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<UserListPreviewDto> page = userFacade.getUserList(query, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userFacade.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<UserDto> getUserDetailsById(@PathVariable Long id) {
        UserDto user = userFacade.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/preview")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<UserPreviewDto> getUserPreviewById(@PathVariable Long id) {
        UserPreviewDto userPreview = userFacade.getUserPreview(id);
        return ResponseEntity.ok(userPreview);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        UserDto updatedUser = userFacade.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userFacade.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
