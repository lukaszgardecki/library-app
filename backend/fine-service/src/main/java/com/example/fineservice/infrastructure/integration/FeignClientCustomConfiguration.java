package com.example.fineservice.infrastructure.integration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;
import java.util.Optional;

@Configuration
public class FeignClientCustomConfiguration {

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public RequestInterceptor authenticationRequestInterceptor() {
        return template -> {
            template.header("X-Source", serviceName);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
                String userId = authentication.getPrincipal().toString();
                template.header("X-User-Id-Encoded", encode(userId));

                Optional<? extends GrantedAuthority> roleOptional = authentication.getAuthorities().stream().findFirst();
                roleOptional.ifPresent(role -> template
                        .header("X-User-Role-Encoded", encode(role.getAuthority().replace("ROLE_", "")))
                );
            }
        };
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
