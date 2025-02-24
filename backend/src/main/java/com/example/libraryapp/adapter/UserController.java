package com.example.libraryapp.adapter;

import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.user.dto.UserDto;
import com.example.libraryapp.domain.user.dto.UserListPreviewDto;
import com.example.libraryapp.domain.user.dto.UserPreviewDto;
import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.example.libraryapp.infrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.libraryapp.domain.user.model.Role.ADMIN;
import static com.example.libraryapp.domain.user.model.Role.USER;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @GetMapping
    @RoleAuthorization({ADMIN})
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> page = userFacade.getAllUsers(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/list")
    @RoleAuthorization({ADMIN})
    public ResponseEntity<Page<UserListPreviewDto>> getAllUserListPreviews(
            @RequestParam(value = "q", required = false) String query,
            Pageable pageable
    ) {
        Page<UserListPreviewDto> page = userFacade.getUserList(query, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userFacade.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/details")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<UserDto> getUserDetailsById(@PathVariable Long id) {
        UserDto user = userFacade.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/preview")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<UserPreviewDto> getUserPreviewById(@PathVariable Long id) {
        UserPreviewDto userPreview = userFacade.getUserPreview(id);
        return ResponseEntity.ok(userPreview);
    }

    @PatchMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto user) {
        UserDto updatedUser = userFacade.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @RoleAuthorization({USER, ADMIN})
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userFacade.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
