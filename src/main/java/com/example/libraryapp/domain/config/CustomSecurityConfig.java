package com.example.libraryapp.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomSecurityConfig {
    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/api/v1/register").permitAll()
                        .anyRequest().permitAll())
                .formLogin(login -> login
                        .loginPage("/login").permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout").permitAll())
                .rememberMe(remember -> remember
                        .key("aBcdeFgHijklMNopqRstUvwxYZ1234567890")
                        .tokenValiditySeconds(60 * 60 * 24 * 30)
                )
                .csrf().disable()
        ;
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}