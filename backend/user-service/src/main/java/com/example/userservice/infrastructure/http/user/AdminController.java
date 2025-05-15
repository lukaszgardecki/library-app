package com.example.userservice.infrastructure.http.user;

import com.example.userservice.core.user.UserFacade;
import com.example.userservice.domain.model.user.UserUpdateAdmin;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.http.user.dto.UserDetailsAdminDto;
import com.example.userservice.infrastructure.http.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
class AdminController {
    private final UserFacade userFacade;
    private final DetailsAggregator detailsAggregator;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailsAdminDto> getUserById(@PathVariable Long id) {
        UserDetailsAdminDto userDetailsAdmin = detailsAggregator.getUserDetailsAdmin(new UserId(id));
        return ResponseEntity.ok(userDetailsAdmin);
    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateAdmin userData) {
        UserDto user = UserMapper.toDto(userFacade.updateUserByAdmin(new UserId(id), userData));
        return ResponseEntity.ok(user);
    }
}
