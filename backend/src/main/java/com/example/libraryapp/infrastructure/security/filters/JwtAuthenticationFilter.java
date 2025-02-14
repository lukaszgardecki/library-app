package com.example.libraryapp.infrastructure.security.filters;

import com.example.libraryapp.application.token.HttpRequestExtractor;
import com.example.libraryapp.application.token.TokenFacade;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenFacade tokenFacade;
    private final HttpRequestExtractor extractor;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (isNotAuthorizedEndpoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractor.extractTokenFromHeader(request);
            String fingerprint = extractor.extractFingerprintFromHeader(request);
            boolean isRefreshTokenRequest = requestURI.endsWith("/refresh-token");
            tokenFacade.validateTokenAndFingerprint(token, fingerprint, isRefreshTokenRequest);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = tokenFacade.getUsernameFrom(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                setAuthentication(request, userDetails);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(
                    request, response,null, new JwtException("Message.ACCESS_DENIED.getMessage()")
            );
        }
    }

    private boolean isNotAuthorizedEndpoint(String requestURI) {
        List<String> publicEndpoints = List.of(
                // H2
                "/h2-console", "/favicon.ico",

                // application endpoints:
                "/authenticate",
                "/register",
                "/books"
        );
        return publicEndpoints.stream().anyMatch(requestURI::contains);
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
