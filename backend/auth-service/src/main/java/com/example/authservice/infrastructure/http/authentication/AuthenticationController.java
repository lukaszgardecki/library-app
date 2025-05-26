package com.example.authservice.infrastructure.http.authentication;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.core.authentication.AuthenticationFacade;
import com.example.authservice.domain.constants.Constants;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.token.TokenInfo;
import com.example.authservice.infrastructure.http.authdetails.AuthDetailsMapper;
import com.example.authservice.infrastructure.http.authdetails.dto.AuthDetailsDto;
import com.example.authservice.infrastructure.http.authentication.dto.AuthDto;
import com.example.authservice.infrastructure.http.authentication.dto.LoginRequest;
import com.example.authservice.infrastructure.http.authentication.dto.LoginResponse;
import com.example.authservice.infrastructure.security.HttpRequestExtractor;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.authservice.domain.model.token.values.TokenType.ACCESS;
import static com.example.authservice.domain.model.token.values.TokenType.REFRESH;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthenticationController {
    private final HttpRequestExtractor extractor;
    private final AuthDetailsFacade authDetailsFacade;
    private final AuthenticationFacade authFacade;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest body,
            HttpServletResponse response
    ) {
        AuthDto auth = AuthenticationMapper.toDto(authFacade.authenticate(new Email(body.getUsername()), new Password(body.getPassword())));
        String cookie = createAuthCookie(auth.cookieName(), auth.cookieValue());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @PostMapping("/refresh")
    ResponseEntity<LoginResponse> refreshToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            HttpServletResponse response
    ) {
        String token = extractor.extractToken(authHeader);
        AuthDto auth = AuthenticationMapper.toDto(authFacade.refreshUserTokens(new TokenInfo(token, REFRESH)));
        String cookie = createAuthCookie(auth.cookieName(), auth.cookieValue());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie);
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @PostMapping("/validate/token")
    ResponseEntity<AuthDetailsDto> validateToken(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader
    ) {
        String token = extractor.extractToken(authHeader);
        UserId userId = authFacade.validateToken(new TokenInfo(token, ACCESS));
        AuthDetailsDto auth = AuthDetailsMapper.toDto(authDetailsFacade.getAuthDetailsByUserId(userId));
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/validate/token-cookie")
    ResponseEntity<AuthDetailsDto> validateTokenAndCookie(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @CookieValue(name = Constants.AUTH_COOKIE_NAME, required = false) String cookie
    ) {
        String token = extractor.extractToken(authHeader);
        UserId userId = authFacade.validateTokenAndCookie(new TokenInfo(token, ACCESS), cookie);
        AuthDetailsDto auth = AuthDetailsMapper.toDto(authDetailsFacade.getAuthDetailsByUserId(userId));
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/logout/{userId}")
    ResponseEntity<Void> logout(@PathVariable Long userId) {
        authFacade.revokeTokensByUserId(new UserId(userId));
        return ResponseEntity.noContent().build();
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
