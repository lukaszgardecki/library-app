package com.example.libraryapp.infrastructure.spring.security.auth;

import com.example.libraryapp.core.token.TokenFacade;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenFacade tokenFacade;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final MessageProviderPort msgProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        String requestURI = request.getRequestURI();
        try {
            String token = tokenFacade.extractTokenFromHeader(request);
            String fingerprint = tokenFacade.extractFingerprintFromHeader(request);
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
                    request, response, null, new JwtException(msgProvider.getMessage(MessageKey.ACCESS_DENIED))
            );
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/ws")
                || path.startsWith("/h2-console")
                || path.startsWith("/favicon.ico")

                // app version:
                || path.contains("/version")

                // application endpoints:
                || path.contains("/authenticate")
                || path.contains("/register")
                || (path.contains("/books") && Objects.equals(request.getMethod(), HttpMethod.GET.name()))

                //fake users:
                || path.contains("/fu");
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
