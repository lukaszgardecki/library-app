package com.example.notificationservice.infrastructure.security;

import com.example.notificationservice.domain.i18n.MessageKey;
import com.example.notificationservice.domain.ports.out.MessageProviderPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
            Long userId = extractUserIdHeader(request);
            String role = extractUserRoleHeader(request);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    Optional.of(role).map(r -> new SimpleGrantedAuthority("ROLE_" + r.trim())).stream().toList()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
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

    private Long extractUserIdHeader(HttpServletRequest request) {
        return extractHeader(request, "X-User-Id-Encoded")
                .map(this::decode)
                .map(Long::parseLong)
                .orElseThrow(() -> new AccessDeniedException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    private String extractUserRoleHeader(HttpServletRequest request) {
        return extractHeader(request, "X-User-Role-Encoded")
                .map(this::decode)
                .orElseThrow(() -> new AccessDeniedException(msgProvider.getMessage(MessageKey.ACCESS_DENIED)));
    }

    private Optional<String> extractHeader(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName));
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
