package com.example.authservice.infrastructure.security;

import com.example.authservice.core.authentication.AuthenticationFacade;
import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.dto.token.TokenInfoDto;
import com.example.authservice.domain.ports.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
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

import static com.example.authservice.domain.model.token.TokenType.ACCESS;
import static com.example.authservice.domain.model.token.TokenType.REFRESH;

@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationFacade authFacade;
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
        try {
            String token = requestExtractor.extractTokenFromRequest(request);
            String cookie = requestExtractor.extractAuthCookie(request);

            if (isRefreshTokenRequest) {
                authFacade.validateTokenAndCookie(new TokenInfoDto(token, REFRESH), cookie);
            } else {
                authFacade.validateTokenAndCookie(new TokenInfoDto(token, ACCESS), cookie);
                setAuthentication(request, token);
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

    private void setAuthentication(HttpServletRequest request, String token) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = authFacade.extractUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}
