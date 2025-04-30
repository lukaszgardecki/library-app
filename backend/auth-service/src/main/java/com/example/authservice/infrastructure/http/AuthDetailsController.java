package com.example.authservice.infrastructure.http;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.dto.auth.CredentialsToSaveDto;
import com.example.authservice.domain.dto.auth.CredentialsUpdateDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.dto.authdetails.AuthDetailsUpdateDto;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/details")
@RequiredArgsConstructor
class AuthDetailsController {
    private final AuthDetailsFacade authDetailsFacade;

    @PostMapping
    public ResponseEntity<Void> saveUserCredentials(
            @RequestBody CredentialsToSaveDto body
    ) {
        authDetailsFacade.createAuthDetails(body);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AuthDetailsDto> getUserAuthByUserId(@PathVariable Long userId) {
        AuthDetailsDto userAuth = authDetailsFacade.getAuthDetailsByUserId(new UserId(userId));
        return ResponseEntity.ok(userAuth);
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<?> updateUserAuth(@PathVariable Long id, @RequestBody AuthDetailsUpdateDto fieldsToUpdate) {
        authDetailsFacade.updateAuthDetails(new UserId(id), fieldsToUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/credentials")
//    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<?> updateUserCredentials(@PathVariable Long id, @RequestBody CredentialsUpdateDto fieldsToUpdate) {
        authDetailsFacade.updateAuthDetails(new UserId(id), fieldsToUpdate);
        return ResponseEntity.noContent().build();
    }
}
