package com.example.apigateway.infrastructure.security;

import com.example.apigateway.domain.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAndFingerprintValidationFilter implements GlobalFilter {
    private final WebClient.Builder webClientBuilder;
    private static final String AUTH_COOKIE_NAME = "auth";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (shouldNotFilter(exchange)) return chain.filter(exchange);

        String token = Optional.of(exchange.getRequest().getHeaders())
                .map(headers -> headers.getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> !authHeader.isBlank())
                .map(authHeader -> "Bearer " + authHeader.substring(7))
                .orElseGet(() -> "");

        WebClient.RequestBodySpec requestBodySpec = webClientBuilder.build()
                    .post()
                    .uri("http://auth-service/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, token);

        Optional.of(exchange.getRequest().getCookies())
                .map(cookies -> cookies.getFirst(AUTH_COOKIE_NAME))
                .map(HttpCookie::getValue)
                .filter(value -> !value.isEmpty())
                .ifPresent(value -> requestBodySpec.cookie(AUTH_COOKIE_NAME, value));

        return requestBodySpec.exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode().is2xxSuccessful()) {
                return clientResponse.bodyToMono(UserAuthDto.class)
                        .flatMap(authResponse -> {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(
                                            authResponse.userId(),
                                            null,
                                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + authResponse.role()))
                                    );
                            return chain.filter(exchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                        });
            } else {
                exchange.getResponse().setStatusCode(clientResponse.statusCode());
                clientResponse.headers().asHttpHeaders().forEach((name, values) -> {
                    exchange.getResponse().getHeaders().addAll(name, values);
                });

                return clientResponse.body(BodyExtractors.toDataBuffers())
                        .flatMap(buffer -> exchange.getResponse().writeWith(Mono.just(buffer)))
                        .then(chain.filter(exchange));
            }
        })
        .onErrorResume(e -> {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        });
    }

    private boolean shouldNotFilter(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        HttpMethod method = exchange.getRequest().getMethod();
        return path.startsWith("/ws")
                || path.startsWith("/h2-console")
                || path.startsWith("/favicon.ico")
                || path.startsWith("/version")
                || path.startsWith("/auth/register")
                || path.startsWith("/auth/login")
                || (path.startsWith("/catalog/books") && HttpMethod.GET.equals(method))
                || path.startsWith("/fu");
    }
}
