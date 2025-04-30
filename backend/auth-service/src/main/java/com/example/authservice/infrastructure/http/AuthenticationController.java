package com.example.authservice.infrastructure.http;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.core.authentication.AuthenticationFacade;
import com.example.authservice.domain.Constants;
import com.example.authservice.domain.dto.auth.LoginRequest;
import com.example.authservice.domain.dto.auth.LoginResponse;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.dto.token.AuthDto;
import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.Password;
import com.example.authservice.domain.model.authdetails.UserId;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthenticationController {
    private final AuthDetailsFacade authDetailsFacade;
    private final AuthenticationFacade authFacade;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest body,
            HttpServletResponse response
    ) {
        AuthDto auth = authFacade.authenticate(new Email(body.getUsername()), new Password(body.getPassword()));
        String cookie = createAuthCookie(auth.cookieName(), auth.cookieValue());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            HttpServletResponse response
    ) {
        String refreshToken = authFacade.extractTokenFromHeader(authHeader);
        AuthDto auth = authFacade.refreshUserTokens(refreshToken);
        String cookie = createAuthCookie(auth.cookieName(), auth.cookieValue());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @PostMapping("/validate")
    public ResponseEntity<AuthDetailsDto> validateToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @CookieValue(name = Constants.AUTH_COOKIE_NAME, required = false) String cookie
    ) {
        String token = authFacade.extractTokenFromHeader(authHeader);
        UserId userId = authFacade.validateTokenAndCookie(token, cookie);
        AuthDetailsDto auth = authDetailsFacade.getAuthDetailsByUserId(userId);
        return ResponseEntity.ok(auth);
    }

    private String createAuthCookie(String cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName, cookieValue)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .httpOnly(true)
                .build()
                .toString();
    }
}
