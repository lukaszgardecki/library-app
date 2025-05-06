package com.example.authservice.infrastructure.security;

import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.ports.MessageProviderPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class GatewayAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final MessageProviderPort msgProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        try {
            validateGatewayHeader(request);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(
                    request, response, null, new AccessDeniedException(msgProvider.getMessage(MessageKey.ACCESS_DENIED))
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/h2-console");
    }

    private void validateGatewayHeader(HttpServletRequest request) {
        extractHeader(request, "X-Source")
                .filter(value -> value.equals("API-Gateway") || value.matches("^[^-]+-service$"))
                .orElseThrow(() -> new AccessDeniedException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    private Optional<String> extractHeader(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName));
    }
}
