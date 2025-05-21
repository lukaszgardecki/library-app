package com.example.authservice.infrastructure.http.authdetails;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.infrastructure.http.authdetails.dto.AuthDetailsDto;
import com.example.authservice.infrastructure.http.authdetails.dto.AuthDetailsUpdateDto;
import com.example.authservice.infrastructure.http.authdetails.dto.CredentialsUpdateDto;
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
    ResponseEntity<AuthDetailsDto> getAuthDetailsByUserId(@PathVariable Long userId) {
        AuthDetailsDto userAuth = AuthDetailsMapper.toDto(authDetailsFacade.getAuthDetailsByUserId(new UserId(userId)));
        return ResponseEntity.ok(userAuth);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.auth.userId.value")
    ResponseEntity<?> updateAuthDetails(@PathVariable Long id, @RequestBody AuthDetailsUpdateDto fieldsToUpdate) {
        authDetailsFacade.updateAuthDetails(new UserId(id), AuthDetailsMapper.toModel(fieldsToUpdate));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/credentials")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.auth.userId.value")
    ResponseEntity<?> updateUserCredentials(@PathVariable Long id, @RequestBody CredentialsUpdateDto fieldsToUpdate) {
        authDetailsFacade.updateUserCredentials(
                new UserId(id), new Email(fieldsToUpdate.username()), new Password(fieldsToUpdate.username())
        );
        return ResponseEntity.noContent().build();
    }
}
