package com.example.authservice.infrastructure.http;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsUpdateDto;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/details")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
class AuthDetailsController {
    private final AuthDetailsFacade authDetailsFacade;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.auth.userId.value")
    ResponseEntity<AuthDetailsDto> getUserAuthByUserId(@PathVariable Long userId) {
        AuthDetailsDto userAuth = authDetailsFacade.getAuthDetailsByUserId(new UserId(userId));
        return ResponseEntity.ok(userAuth);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.auth.userId.value")
    ResponseEntity<?> updateUserAuth(@PathVariable Long id, @RequestBody AuthDetailsUpdateDto fieldsToUpdate) {
        authDetailsFacade.updateAuthDetails(new UserId(id), fieldsToUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/credentials")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.auth.userId.value")
    ResponseEntity<?> updateUserCredentials(@PathVariable Long id, @RequestBody CredentialsUpdateDto fieldsToUpdate) {
        authDetailsFacade.updateAuthDetails(new UserId(id), fieldsToUpdate);
        return ResponseEntity.noContent().build();
    }
}
