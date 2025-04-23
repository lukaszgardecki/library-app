package com.example.authservice.infrastructure.http;

import com.example.authservice.core.auth.AuthenticationFacade;
import com.example.authservice.domain.model.auth.Email;
import com.example.authservice.domain.model.auth.Password;
import com.example.authservice.domain.model.auth.UserId;
import com.example.authservice.core.token.TokenFacade;
import com.example.authservice.domain.dto.auth.*;
import com.example.authservice.domain.dto.token.TokenAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthenticationController {
    // TODO: 09.12.2024  wywalić te fasady stąc i zostawić tylko AuthenticationFacade, reszta do use casów!!!!
    // TODO: 09.12.2024 ewentualnie zostawić ten extractor bo to wzwiązane z webem
    private final AuthenticationFacade authFacade;
    private final TokenFacade tokenFacade;

    @PostMapping("/register")
    public ResponseEntity<Void> saveUserCredentials(
            @RequestBody CredentialsToSaveDto body
    ) {
        authFacade.saveUserCredentials(body);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest body,
            HttpServletResponse response
    ) {
        TokenAuth auth = authFacade.authenticate(
                new Email(body.getUsername()), new Password(body.getPassword())
        );
        response.addHeader(auth.cookie().getName(), auth.cookie().getValue());
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenFacade.extractTokenFromHeader(request);
        TokenAuth auth = tokenFacade.refreshUserTokens(token);
        response.addHeader(auth.cookie().getName(), auth.cookie().getValue());
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAuthDto> getUserAuthByUserId(@PathVariable Long userId) {
        UserAuthDto userAuth = authFacade.getUserAuthByUserId(new UserId(userId));
        return ResponseEntity.ok(userAuth);
    }

    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<?> updateUserAuth(@PathVariable Long id, @RequestBody UserAuthUpdateDto fieldsToUpdate) {
        authFacade.updateUserAuth(new UserId(id), fieldsToUpdate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/credentials")
//    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and #id == authentication.principal.id)")
    public ResponseEntity<?> updateUserCredentials(@PathVariable Long id, @RequestBody CredentialsUpdateDto fieldsToUpdate) {
        authFacade.updateUserCredentials(new UserId(id), fieldsToUpdate);
        return ResponseEntity.noContent().build();
    }
}
