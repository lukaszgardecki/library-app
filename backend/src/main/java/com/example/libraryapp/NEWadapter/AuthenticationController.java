package com.example.libraryapp.NEWadapter;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.token.HttpRequestExtractor;
import com.example.libraryapp.NEWapplication.token.TokenFacade;
import com.example.libraryapp.NEWdomain.auth.dto.LoginRequest;
import com.example.libraryapp.NEWdomain.auth.dto.LoginResponse;
import com.example.libraryapp.NEWdomain.token.dto.AuthTokensDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class AuthenticationController {
    // TODO: 09.12.2024  wywalić te fasady stąc i zostawić tylko AuthenticationFacade, reszta do use casów!!!!
    // TODO: 09.12.2024 ewentualnie zostawić ten extractor bo to wzwiązane z webem
    private final AuthenticationFacade authFacade;
    private final HttpRequestExtractor extractor;
    private final TokenFacade tokenFacade;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest body,
            HttpServletResponse response
    ) {
        AuthTokensDto auth = authFacade.authenticate(body.getUsername(), body.getPassword());
        response.addHeader(auth.cookie().getName(), auth.cookie().getValue());
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = extractor.extractTokenFromHeader(request);
        AuthTokensDto auth = tokenFacade.refreshUserTokens(token);
        response.addHeader(auth.cookie().getName(), auth.cookie().getValue());
        return ResponseEntity.ok(new LoginResponse(auth.accessToken(), auth.refreshToken()));
    }


}
