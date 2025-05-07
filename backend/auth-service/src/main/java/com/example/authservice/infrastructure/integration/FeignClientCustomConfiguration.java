package com.example.authservice.infrastructure.integration;

import com.example.authservice.core.authdetails.AuthDetailsFacade;
import com.example.authservice.domain.dto.authdetails.AuthDetailsDto;
import com.example.authservice.domain.model.authdetails.UserId;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class FeignClientCustomConfiguration {
    public static final String USER_ID_ATTR_NAME = "userIdPathVariable";
    private final AuthDetailsFacade authDetailsFacade;
    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public RequestInterceptor authenticationRequestInterceptor() {
        return this::applyAuthenticationHeaders;
    }

    private void applyAuthenticationHeaders(RequestTemplate template) {
        template.header("X-Source", serviceName);

        Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attrs -> attrs.getAttribute(USER_ID_ATTR_NAME, RequestAttributes.SCOPE_REQUEST))
                .map(Object::toString)
                .map(Long::parseLong)
                .ifPresentOrElse(
                        userId -> applyUserSpecificHeaders(template, userId),
                        () -> applyDefaultAuthenticationHeaders(template)
                );
    }

    private void applyUserSpecificHeaders(RequestTemplate template, Long userId) {
        AuthDetailsDto authDetails = authDetailsFacade.getAuthDetailsByUserId(new UserId(userId));
        template.header("X-User-Id-Encoded", encode(String.valueOf(userId)));
        template.header("X-User-Role-Encoded", encode(authDetails.role().name()));
    }

    private void applyDefaultAuthenticationHeaders(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            template.header("X-User-Id-Encoded", encode(authentication.getPrincipal().toString()));

            Optional<? extends GrantedAuthority> roleOptional = authentication.getAuthorities().stream().findFirst();
            roleOptional.ifPresent(role -> template
                    .header("X-User-Role-Encoded", encode(role.getAuthority().replace("ROLE_", "")))
            );
        }
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
