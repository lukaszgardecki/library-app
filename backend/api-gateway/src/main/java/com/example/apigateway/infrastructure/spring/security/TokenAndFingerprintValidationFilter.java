package com.example.apigateway.infrastructure.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class TokenAndFingerprintValidationFilter implements GatewayFilterFactory<TokenAndFingerprintValidationFilter.Config> {
    private final WebClient.Builder webClientBuilder;
    private static final String AUTH_COOKIE_NAME = "auth";

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String token = Optional.of(exchange.getRequest().getHeaders())
                    .map(headers -> headers.getFirst(HttpHeaders.AUTHORIZATION))
                    .filter(authHeader -> !authHeader.isBlank())
                    .map(authHeader -> "Bearer " + authHeader.substring(7))
                    .orElseGet(() -> "");

            WebClient.RequestBodySpec requestBodySpec = webClientBuilder.build()
                    .post()
                    .uri("http://auth-service/auth/validate/token-cookie")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .header("X-Source", "API-Gateway");

            Optional.of(exchange.getRequest().getCookies())
                    .map(cookies -> cookies.getFirst(AUTH_COOKIE_NAME))
                    .map(HttpCookie::getValue)
                    .filter(value -> !value.isEmpty())
                    .ifPresent(value -> requestBodySpec.cookie(AUTH_COOKIE_NAME, value));

            Mono<Void> responseHandling = requestBodySpec.exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode().is2xxSuccessful()) {
                            return clientResponse.bodyToMono(UserAuthDto.class)
                                    .flatMap(authResponse -> {
                                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                                .header("X-User-Id-Encoded", encode(authResponse.userId().toString()))
                                                .header("X-User-Role-Encoded", encode(authResponse.role().toString()))
                                                .build();
                                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                                    });
                        } else {
                            exchange.getResponse().setStatusCode(clientResponse.statusCode());
                            clientResponse.headers().asHttpHeaders().forEach((name, values) -> {
                                exchange.getResponse().getHeaders().addAll(name, values);
                            });

                            return DataBufferUtils.join(clientResponse.body(BodyExtractors.toDataBuffers()))
                                    .flatMap(joinedBuffer -> {
                                        try {
                                            return exchange.getResponse().writeWith(Mono.just(joinedBuffer))
                                                    .doFinally(signalType -> DataBufferUtils.release(joinedBuffer));
                                        } catch (Exception e) {
                                            DataBufferUtils.release(joinedBuffer);
                                            return Mono.error(e);
                                        }
                                    })
                                    .then(chain.filter(exchange));
                        }
                    });
            return responseHandling.onErrorResume(e -> {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            });
        };
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    static class Config { }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.emptyList();
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    private record UserAuthDto(
            String username,
            String role,
            String status,
            Long userId
    ) {
    }
}
