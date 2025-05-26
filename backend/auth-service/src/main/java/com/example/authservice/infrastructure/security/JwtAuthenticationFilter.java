package com.example.authservice.infrastructure.security;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.core.authentication.AuthenticationFacade;
import com.example.authservice.domain.i18n.MessageKey;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.token.TokenInfo;
import com.example.authservice.domain.ports.out.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static com.example.authservice.domain.model.token.values.TokenType.ACCESS;
import static com.example.authservice.domain.model.token.values.TokenType.REFRESH;

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationFacade authFacade;
    private final AuthDetailsFacade authDetailsFacade;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final HttpRequestExtractor requestExtractor;
    private final MessageProviderPort msgProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        String requestURI = request.getRequestURI();
        boolean isRefreshTokenRequest = requestURI.endsWith("/auth/refresh");
        boolean isServiceRequest = isServiceRequest(request);
        try {
            if (isServiceRequest) {
                setServiceAuthentication(request);
                filterChain.doFilter(request, response);
                return;
            }
            String token = requestExtractor.extractTokenFromRequest(request);
            String cookie = requestExtractor.extractAuthCookie(request);

            if (isRefreshTokenRequest) {
                authFacade.validateTokenAndCookie(new TokenInfo(token, REFRESH), cookie);
            } else {
                authFacade.validateTokenAndCookie(new TokenInfo(token, ACCESS), cookie);
                setUserAuthentication(request, token);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(
                    request, response, null, new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED))
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/h2-console")
            || path.startsWith("/auth/register")
            || path.startsWith("/auth/login")
            || path.startsWith("/auth/logout")
            || path.startsWith("/auth/validate");
    }

    private void setServiceAuthentication(HttpServletRequest request) {
        String username = authDetailsFacade.getAuthDetailsByUserId(new UserId(extractUserIdHeader(request))).getEmail().value();
        setAuthentication(username, request);
    }

    private void setUserAuthentication(HttpServletRequest request, String token) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = authFacade.extractUsernameFromToken(token);
            setAuthentication(username, request);
        }
    }

    private void setAuthentication(String username, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private boolean isServiceRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-Source"))
                .filter(value -> value.matches("^[^-]+-service$"))
                .isPresent();
    }

    private Long extractUserIdHeader(HttpServletRequest request) {
        return extractHeader(request, "X-User-Id-Encoded")
                .map(this::decode)
                .map(Long::parseLong)
                .orElseThrow(() -> new AccessDeniedException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    private Optional<String> extractHeader(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName));
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
