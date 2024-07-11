package com.example.libraryapp.domain.config;

import com.example.libraryapp.domain.token.TokenService;
import com.example.libraryapp.domain.token.TokenType;
import com.example.libraryapp.management.Message;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if ((authHeader == null || !authHeader.startsWith(TokenType.BEARER.getPrefix())) &&  !requestURI.endsWith("/refresh-token")) {
            filterChain.doFilter(request,response);
            return;
        }
        try {
            String token = extractToken(request);
            String fingerprint = extractFingerprint(request);
            boolean tokenFgpAreOK = tokenService.validateTokenAndFgp(token, fingerprint);
            boolean authenticationIsAbsent = SecurityContextHolder.getContext().getAuthentication() == null;

            if (tokenFgpAreOK && authenticationIsAbsent) {
                UserDetails userDetails = loadUserDetailsFromToken(token);
                setAuthentication(request, userDetails);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(
                    request, response,null, new JwtException(Message.ACCESS_DENIED.getMessage())
            );
        }
    }

    private UserDetails loadUserDetailsFromToken(String token) {
        String username = tokenService.extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    private String extractToken(HttpServletRequest request) {
        return tokenService.findToken(request)
                .filter(getTokenValidator(request.getRequestURI()))
                .orElseThrow(() -> new JwtException(Message.ACCESS_DENIED.getMessage()));
    }

    private String extractFingerprint(HttpServletRequest request) {
        return tokenService.findFingerprint(request)
                .orElseThrow(() -> new JwtException(Message.ACCESS_DENIED.getMessage()));
    }

    private Predicate<String> getTokenValidator(String requestURI) {
        return requestURI.endsWith("/refresh-token") ?
                tokenService::isRefreshTokenValidInDB :
                tokenService::isAccessTokenValidInDB;
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
